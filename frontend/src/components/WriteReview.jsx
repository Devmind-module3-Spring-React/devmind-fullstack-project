import React, {useEffect, useState} from 'react';
import {
    Box, Container, Typography, TextField, Button, Rating, Paper,
    CircularProgress, Snackbar, Alert
} from '@mui/material';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router';

const WriteReview = () => {
    const { serviceId } = useParams();
    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');
    const [rating, setRating] = useState(0);
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

        if (!title || !body || rating === 0) {
            setError('Please fill in all fields');
            return;
        }

        setLoading(true);
        setError(null);

        try {
            const response = await fetch('http://localhost:8081/api/v1/reviews/create-new-review', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwt}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    title,
                    body,
                    rating,
                    serviceId,
                    userId: user.id
                })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.error || 'Failed to submit review');
            }

            setTitle('');
            setBody('');
            setRating(0);
            setSuccess(true);
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
                    Write a Review
                </Typography>

                <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
                    <Typography component="legend">Rating</Typography>
                    <Rating
                        name="rating"
                        value={rating}
                        onChange={(event, newValue) => setRating(newValue)}
                        precision={1}
                        size="large"
                        sx={{ mt: 1 }}
                    />

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="title"
                        label="Review Title"
                        name="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />

                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="body"
                        name="body"
                        label="Your Review"
                        multiline
                        rows={6}
                        value={body}
                        onChange={(e) => setBody(e.target.value)}
                    />

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} /> : 'Submit Review'}
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
                    Review submitted successfully!
                </Alert>
            </Snackbar>
        </Container>
    );
};

export default WriteReview;
