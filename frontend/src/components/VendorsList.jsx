import Box from "@mui/material/Box";
import {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {Card, CardContent, CircularProgress} from "@mui/material";
import {useNavigate} from "react-router";

const VendorsList = () => {
    const [vendors, setVendors] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8081/vendors")
            .then((response) => response.json()) // Convert response to JSON
            .then((data) => {
                setVendors(data);  // Set vendors data
                setLoading(false);  // Set loading to false once data is fetched
            })
            .catch((error) => {
                console.error("There was an error fetching the vendors!", error);
                setLoading(false);  // Stop loading on error
            });
    }, []);

    if (loading) {
        return <CircularProgress />;
    }

    return (
        <Box sx={{ padding: 3 }}>
            {/*<Typography variant="h4" gutterBottom>Furnizori de Servicii</Typography>*/}
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2, // Creates a gap between the cards
                }}
            >
                {vendors.map((vendor) => (

                    <Box key={vendor.id} sx={{width: {xs: '100%', sm: '48%', md: '30%'}}}>
                        <Card
                            key={vendor.id}
                            onClick={() => navigate(`/vendors/${vendor.id}`)}
                        >

                            <CardContent>
                                <Typography variant="h6">{vendor.companyName}</Typography>
                                <Typography variant="body2" color="textSecondary">{vendor.location}</Typography>
                                <Typography variant="body2" sx={{mt: 2}}>{vendor.description}</Typography>
                                <Typography variant="body1" color="primary" sx={{mt: 2}}>
                                    Rating: {vendor.rating ? vendor.rating : 'N/A'}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                ))}
            </Box>
        </Box>
    );
}
export default VendorsList;