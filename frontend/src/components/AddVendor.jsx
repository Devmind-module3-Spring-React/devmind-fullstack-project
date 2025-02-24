import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {addVendor} from "../redux/reducers/reducers.js";
import {useNavigate} from "react-router";
import {Button, Card, CardContent, TextField} from "@mui/material";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

const AddVendor = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    //user is stored in the auth slice from redux. so not state.user but state.auth.user
    const user = useSelector((state) => state.auth.user);

    const jwt = useSelector((state) => state.auth.jwt);

    // Redirect to login if not authenticated - so that user can't add vendor if it is not logged in
    useEffect(() => {
        if (!jwt) {
            navigate("/login");
        }
    }, [jwt, navigate]);

    const userId = user?.id;

    const [formData, setFormData] = useState({
        companyName: '',
        companyEmail: '',
        location: '',
        websiteUrl: '',
        phoneNumber: '',
        description: ''
    });

    const [errors, setErrors] = useState({});

    const validateForm = () => {
        const newErrors = {};

        if (!formData.companyName.trim()) {
            newErrors.companyName = 'Company name is required';
        }

        if (!formData.location.trim()) {
            newErrors.location = 'Location is required';
        }

        if (!formData.websiteUrl.trim()) {
            newErrors.websiteUrl = 'Website URL is required';
        }

        setErrors(newErrors);

        //evaluates to true if newErrors has no keys, meaning there are no validation errors.
        //if newErrors is { companyName: 'Company name is required', location: 'Location is required' }, Object.keys(newErrors) will return ['companyName', 'location'].
        return Object.keys(newErrors).length === 0;
    };

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        setErrors(prev => ({
            ...prev,
            [name]: ''
        }));
    };


    const submitVendor = (e) => {
        e.preventDefault();

        if (!validateForm) {
            return;
        }

        fetch('http://localhost:8081/api/v1/vendors/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...formData,
                createdByUserId: userId
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to add vendor');
                }
                return response.json();
            })
            .then(newVendor => {
                dispatch(addVendor(newVendor));
                navigate('/vendors'); //get back to all vendors route
            })
            .catch(error => {
                console.error('Error adding Vendor:', error);
                setErrors( prev => ({
                    ...prev,
                    submit:'Failed to add vendor. Please try again'
                }));
            });
    };

    return (
        <Box sx={{ blockSize: '300px', padding: 3, maxWidth: '70%', margin: '0 auto' }}>
            <Card>
                <CardContent sx={{ maxWidth: '100%' }}>
                    <Typography variant="h5" gutterBottom>
                        Adauga un furnizor
                    </Typography>

                    <form onSubmit={submitVendor}>

                            <TextField
                                fullWidth
                                margin="normal"
                                name="companyName"
                                label="Company Name"
                                value={formData.companyName}
                                onChange={handleInputChange}
                                error={!!errors.companyName}
                                helperText={errors.companyName}
                                required
                            />

                            <TextField
                                fullWidth
                                margin="normal"
                                name="companyEmail"
                                label="Company Email"
                                type="email" //adds validation for email
                                value={formData.companyEmail}
                                onChange={handleInputChange}
                            />

                            <TextField
                                fullWidth
                                margin="normal"
                                name="location"
                                label="Location"
                                value={formData.location}
                                onChange={handleInputChange}
                                error={!!errors.location}
                                helperText={errors.location}
                                required
                            />

                            <TextField
                                fullWidth
                                margin="normal"
                                name="websiteUrl"
                                label="Website URL"
                                value={formData.websiteUrl}
                                onChange={handleInputChange}
                                error={!!errors.websiteUrl}
                                helperText={errors.websiteUrl}
                                required
                            />

                            <TextField
                                fullWidth
                                margin="normal"
                                name="phoneNumber"
                                label="Phone Number"
                                value={formData.phoneNumber}
                                onChange={handleInputChange}
                            />

                            {errors.submit && (
                                <Typography color="error">
                                    {errors.submit}
                                </Typography>
                            )}

                            <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end', mt: 2 }}>
                                <Button
                                    type="button"
                                    onClick={() => navigate('/vendors')}
                                    variant="outlined"
                                >
                                    Cancel
                                </Button>
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                >
                                    Add Vendor
                                </Button>

                        </Box>
                    </form>
                </CardContent>
            </Card>
        </Box>
    );
};

export default AddVendor;