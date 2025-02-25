// VendorServices.jsx
import React, { useState, useEffect } from 'react';
import {useLocation, useNavigate, useParams} from 'react-router';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import {Button, Card, CardContent, CircularProgress, Rating} from '@mui/material';
import {useSelector} from "react-redux";


const VendorService = () => {
    const { vendorId } = useParams();
    const [vendorServices, setVendorServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const location = useLocation();  //  Get the navigation state
    const servicesLink = location.state?.servicesLink; // Get the HATEOAS link from navigation state
    const navigate = useNavigate();

    const vendors = useSelector(state => state.vendors.vendors);

    const [vendorName, setVendorName] = useState("Unknown Vendor");


    useEffect(() => {
        //filter vendor from redux state
        const vendor = vendors.find(v => v.id === parseInt(vendorId));
        if (vendor) {
            setVendorName(vendor.companyName); // Update vendor name from Redux
        } else {
            // If not in Redux (e.g. after page refresh) - fetch it from API
            fetch(`http://localhost:8081/api/v1/vendors/${vendorId}`)
                .then(response => response.json())
                .then(data => {
                    setVendorName(data.companyName);
                })
                .catch(error => {
                    console.error('Error fetching vendor details:', error);
                });
        }
    }, [vendors, vendorId]);

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

    const handleWriteReview = (serviceId) => {
        navigate(`/services/${serviceId}/write-review`);
    };

    const handleViewReviews = (serviceId) => {
        navigate(`/services/${serviceId}/reviews`);
    };

    const navigateToAddNewService = () => {
        navigate(`/vendors/${vendorId}/services/add`);
    };

    return (
        <Box sx={{ padding: 3 }}>
            {/* Highlighted Vendor Name */}
            <Typography
                variant="h5"
                sx={{
                    color: 'inherit', // Keeps the default text color
                    textAlign: 'left',
                    mb: 3,
                    letterSpacing: '1.5px', // Adds some spacing between letters
                    lineHeight: '1.2', // Adjusts the line height for better readability
                }}>
                Servicii oferite de <span style={{ color: '#6A5ACD', fontWeight: 'bold',}}>{vendorName}</span>
                    </Typography>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2, // Creates a gap between the cards
                }}
            >
                {vendorServices.map((service) => (
                    <Card key={service.id} sx={{width: {xs: '100%', sm: '48%', md: '30%'}}}>

                        <CardContent>
                            <Typography variant="h6">{service.name}</Typography>
                            <Typography variant="body2" color="textSecondary">{service.description}</Typography>
                            <Box sx={{mt: 2, display: 'flex', alignItems: 'center'}}>
                                <Rating
                                    value={service.rating || 0}
                                    precision={0.1}
                                    readOnly={true}
                                />
                                <Typography variant="body1" color="primary" sx={{ml: 1}}>
                                    {service.rating ? service.rating.toFixed(1) : 'N/A'}
                                </Typography>
                            </Box>

                            <Box sx={{mt: 2, display: 'flex', gap: 1}}>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={() => handleWriteReview(service.id)}
                                >
                                    Scrie Review
                                </Button>

                                <Button
                                    variant="outlined"
                                    color="secondary"
                                    onClick={() => handleViewReviews(service.id)}
                                >
                                    Vezi Review-uri
                                </Button>
                            </Box>
                        </CardContent>
                    </Card>
                ))}
            </Box>
            <Button
                variant="contained"
                color="primary"
                onClick={navigateToAddNewService}
                sx={{ marginTop: 2}}
            >
                Adauga un nou serviciu
            </Button>
        </Box>
    );
}

export default VendorService;
