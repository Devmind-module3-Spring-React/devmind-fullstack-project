// VendorServices.jsx
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { Card, CardContent, CircularProgress } from '@mui/material';

const VendorServices = () => {
    const { vendorId } = useParams();
    const [vendorServices, setVendorServices] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8081/vendors/${vendorId}`)
            .then(response => response.json())
            .then(data => {
                setVendorServices(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching vendor services:', error);
                setLoading(false);
            });
    }, [vendorId]);

    if (loading) {
        return <CircularProgress />;
    }

    return (
        <Box sx={{ padding: 3 }}>
            <Typography variant="h4" gutterBottom>Services</Typography>
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
                        </CardContent>
                    </Card>
                ))}
            </Box>
        </Box>
    );
}

export default VendorServices;
