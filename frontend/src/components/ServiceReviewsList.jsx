import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import {
    Container, Typography, Box, Card, CardContent, CircularProgress, Rating
} from '@mui/material';

const ServiceReviewsList = () => {
    const { serviceId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8081/api/v1/reviews/service/${serviceId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch reviews');
                }
                return response.json();
            })
            .then(data => {
                setReviews(data);
            })
            .catch(err => {
                setError(err.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [serviceId])

    if (loading) {
        return (
            <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh' }}>
                <CircularProgress />
            </Container>
        );
    }

    if (error) {
        return <Typography color="error">{error}</Typography>;
    }

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
            <Typography variant="h4" gutterBottom>Service Reviews</Typography>
            <Box sx={{ mt: 3 }}>
                {reviews.length === 0 ? (
                    <Typography>No reviews yet.</Typography>
                ) : (
                    reviews.map((review) => (
                        <Card key={review.id} sx={{ mb: 2 }}>
                            <CardContent>
                                <Typography variant="h6">{review.title}</Typography>
                                <Rating value={review.rating} precision={0.5} readOnly />
                                <Typography variant="body1" sx={{ mt: 1 }}>{review.body}</Typography>
                                <Typography variant="body2" color="textSecondary" sx={{ mt: 1 }}>
                                    By {review.username} on {new Date(review.createdAt).toDateString()}
                                </Typography>
                            </CardContent>
                        </Card>
                    ))
                )}
            </Box>
        </Container>
    );
};

export default ServiceReviewsList;
