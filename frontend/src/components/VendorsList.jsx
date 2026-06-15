import Box from "@mui/material/Box";
import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {Button, Card, CardContent, CircularProgress} from "@mui/material";
import {useNavigate} from "react-router";
import { useDispatch, useSelector } from "react-redux";
import {setVendors} from "../redux/reducers/reducers.js";

const VendorsList = () => {
    const vendors = useSelector((state) => state.vendors.vendors);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    // const [vendorLinks, setVendorLinks] = useState({}); // Stores HATEOAS links for each vendor


    useEffect(() => {
        fetch("http://localhost:8081/api/v1/vendors")
            .then((response) => response.json())
            .then((data) => {
                dispatch(setVendors(data)); //dispatch action to the store. action passes through the setVendor reducer
                setLoading(false);
            })
            .catch((error) => {
                console.error("There was an error fetching the vendors!", error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <CircularProgress/>;
    }

    const navigateToServices = (vendor) => {
        // Use the HATEOAS services link directly instead of fetching http://localhost:8081/api/v1/vendors/{vendorId}/services
        //HATEOAS response when calling http://localhost:8081/api/v1/vendors has links attached.
        // The link with rel name "services" is of interest in this case -> contains the backend route to vendor's services: http://localhost:8081/api/v1/vendors/{vendorId}
        const servicesLink = vendor.links.find(link => link.rel === "services")?.href

        if (servicesLink) {
            navigate(`/vendors/${vendor.id}/services`, {state: {servicesLink}});
        } else {
            console.error(`No services link found for vendor ID: ${vendor.id}`);
        }
    };

    const navigateToAddNewVendor = () => {
            navigate(`/vendors/add`);
    };

    return (
        <Box sx={{padding: 3}}>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2, // Add gap between the cards
                }}
            >
                {vendors.map((vendor) => (

                    <Box key={vendor.id} sx={{width: {xs: '100%', sm: '48%', md: '30%'}}}>
                        <Card
                            onClick={() => navigateToServices(vendor)}
                            sx={{cursor: 'pointer', padding: 2}}
                        >

                            <CardContent>
                                <Typography variant="h6">{vendor.companyName}</Typography>
                                <Typography variant="body2" color="textSecondary">{vendor.location}</Typography>
                                <Typography variant="body2" sx={{mt: 2}}>{vendor.description}</Typography>
                                <Typography variant="body2" sx={{mt: 2}}>{vendor.phoneNumber}</Typography>
                                <Typography variant="body2" sx={{mt: 2, wordBreak: 'break-all'}}>
                                    <a
                                        onClick={(event) => event.stopPropagation()} // Prevents Card's onClick from triggering when clicking vendor's website
                                        href={vendor.websiteUrl}
                                        target="_blank" rel="noopener noreferrer"
                                        style={{
                                            color: 'blue',
                                            textDecoration: 'underline',
                                            wordBreak: 'break-all',
                                            display: 'inline-block',
                                            maxWidth: '100%',
                                        }}>
                                        {vendor.websiteUrl}
                                    </a>
                                </Typography>
                                <Typography variant="body1" color="primary" sx={{mt: 2}}>
                                    Rating: {vendor.rating ? vendor.rating.toFixed(1) : 'No reviews yet'}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                ))}
            </Box>
            <Button
                variant="contained"
                color="primary"
                onClick={navigateToAddNewVendor}
                sx={{ marginTop: 2}}
            >
                Adauga un nou furnizor
            </Button>
        </Box>
    );
}
export default VendorsList;