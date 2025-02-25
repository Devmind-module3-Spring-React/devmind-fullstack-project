import React, {useEffect, useState} from 'react';
import {
    Box, Container, Typography, TextField, Button, Paper,
    CircularProgress, Snackbar, Alert
} from '@mui/material';
import { useSelector } from 'react-redux';
import {replace, useNavigate, useParams} from 'react-router';

const AddVendorService = () => {
    const [name, setName] = useState('');
    const {vendorId} = useParams();
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const user = useSelector((state) => state.auth.user);
    const jwt = useSelector((state) => state.auth.jwt);
    const navigate = useNavigate();

    //prevent access if not logged in
    //When using { replace: true } the login page replaces the current history entry, so pressing "Back" won't return the user to the review page.
    useEffect(() => {
        if (!jwt) {
            navigate('/login', { replace: true });  // Redirect if the user is not authenticated
        }
    }, [jwt, navigate]);  // Dependencies ensure it only runs when jwt changes; navigate is added for preventing bugs and using latest reference

    const handleSubmit = async (e) => {
        e.preventDefault();

        // if (!name || !description) {
        //     setError('Este necesara completarea campurilor marcate cu *');
        //     return;
        // }

        setLoading(true);
        setError(null);

        try {
            //backticks should be used when using variables in the path ->> ` instead of '. or else the variable won't work
            const response = await fetch(`http://localhost:8081/api/v1/vendors/${vendorId}/services/add`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwt}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name,
                    description,
                    vendorId,
                    userId: user.id
                })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Serviciul furnizorului nu a putut fi adaugat. Te rugam sa reincerci.');
            }

            setName('');
            setDescription('');
            setSuccess(true);
            navigate(`/vendors/${vendorId}/services`, { replace: true });
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleCloseSnackbar = () => {
        setSuccess(false);
        setError(null);
    };

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Paper elevation={3} sx={{ p: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Creaza un nou serviciu
                </Typography>

                <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="title"
                        label="Serviciul folosit"
                        name="title"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="body"
                        name="body"
                        label="Descrie pe scurt serviciul"
                        multiline
                        rows={6}
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} /> : 'Adauga serviciu'}
                    </Button>
                </Box>
            </Paper>

            <Snackbar open={!!error} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                <Alert onClose={handleCloseSnackbar} severity="error" sx={{ width: '100%' }}>
                    {error}
                </Alert>
            </Snackbar>

            <Snackbar open={success} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                    Noul serviciu a fost adaugat cu succes!
                </Alert>
            </Snackbar>
        </Container>
    );
};

export default AddVendorService;
