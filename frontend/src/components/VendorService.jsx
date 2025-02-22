// VendorServices.jsx
import React, { useState, useEffect } from 'react';
import {useLocation, useParams} from 'react-router';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { Card, CardContent, CircularProgress, Rating } from '@mui/material';

const VendorServices = () => {
    const { vendorId } = useParams();
    const [vendorServices, setVendorServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const location = useLocation();  //  Get the navigation state
    const servicesLink = location.state?.servicesLink; // Get the HATEOAS link from navigation state

    useEffect(() => {
        //Added fetchUrl for the case when user decides to manually navigate using browserURL
        const fetchUrl = servicesLink || `http://localhost:8081/api/v1/vendors/${vendorId}/services`;

        fetch(fetchUrl)
            .then(response => response.json())
            .then(data => {
                setVendorServices(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching vendor services:', error);
                setLoading(false);
            });
    }, [servicesLink,vendorId]);

    if (loading) {
        return <CircularProgress />;
    }

    return (
        <Box sx={{ padding: 3 }}>
            <Typography variant="h4" gutterBottom>Servicii</Typography>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2, // Creates a gap between the cards
                }}
            >
                {vendorServices.map((service) => (
                    <Card key={service.id} sx={{ width: { xs: '100%', sm: '48%', md: '30%' } }}>
                        <CardContent>
                            <Typography variant="h6">{service.name}</Typography>
                            <Typography variant="body2" color="textSecondary">{service.description}</Typography>
                            <Box sx={{ mt: 2, display: 'flex', alignItems: 'center' }}>
                                <Rating
                                    value={service.rating || 0}
                                    precision={0.1}
                                    readOnly={true}
                                />
                                <Typography variant="body1" color="primary" sx={{ ml: 1 }}>
                                    {service.rating ? service.rating.toFixed(1) : 'N/A'}
                                </Typography>
                            </Box>
                        </CardContent>
                    </Card>
                ))}
            </Box>
        </Box>
    );
}

export default VendorServices;
