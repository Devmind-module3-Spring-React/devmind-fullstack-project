import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { useSelector } from 'react-redux';
import {
    Container, Typography, Box, Card, CardContent, CircularProgress, Rating, Button, Dialog,
    DialogTitle, DialogContent, DialogActions, TextField
} from '@mui/material';

const ServiceReviewsList = () => {
    const { serviceId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editDialogOpen, setEditDialogOpen] = useState(false);
    const [currentReview, setCurrentReview] = useState(null);
    const [editTitle, setEditTitle] = useState('');
    const [editBody, setEditBody] = useState('');
    const [editRating, setEditRating] = useState(0);

    // Get the current user
    const user = useSelector((state) => state.auth.user);
    const jwt = useSelector((state) => state.auth.jwt);

    useEffect(() => {
        fetchReviews();
    }, [serviceId]);

    const fetchReviews = () => {
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
    };

    const handleEditClick = (review) => {
        setCurrentReview(review);
        setEditTitle(review.title);
        setEditBody(review.body);
        setEditRating(review.rating);
        setEditDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setEditDialogOpen(false);
        setCurrentReview(null);
    };

    const handleSaveEdit = () => {
        if (!currentReview) return;

        // Create updated review object
        const updatedReview = {
            ...currentReview,
            title: editTitle,
            body: editBody,
            rating: editRating
        };

        // Send PUT request to update the review
        fetch(`http://localhost:8081/api/v1/reviews/${currentReview.id}/edit`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                // Add any authorization headers if needed
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify(updatedReview)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to update review');
                }
                return response.json();
            })
            .then(() => {
                // Update the local state with the edited review
                const updatedReviews = reviews.map(review =>
                    review.id === currentReview.id ? updatedReview : review
                );
                setReviews(updatedReviews);
                handleCloseDialog();
            })
            .catch(err => {
                setError(err.message);
            });
    };

    const handleDeleteClick = (review) => {
        if (!window.confirm('Are you sure you want to delete this review?')) {
            return;
        }

        fetch(`http://localhost:8081/api/v1/reviews/${review.id}/delete`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete review');
                }
                return response.json();
            })
            .then(() => {
                // Update the local state by removing the deleted review
                const updatedReviews = reviews.filter(r => r.id !== review.id);
                setReviews(updatedReviews);
            })
            .catch(err => {
                setError(err.message);
            });
    };

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
                                <Box sx={{ mt: 1, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                    <Typography variant="body2" color="textSecondary">
                                        By {review.username} on {new Date(review.createdAt).toDateString()}
                                    </Typography>
                                    {user && user.username === review.username && (
                                        <Box sx={{ display: 'flex', gap: 1 }}>
                                            <Button
                                                variant="outlined"
                                                size="small"
                                                onClick={() => handleEditClick(review)}
                                            >
                                                Edit Review
                                            </Button>
                                            <Button
                                                variant="outlined"
                                                size="small"
                                                color="error"
                                                onClick={() => handleDeleteClick(review)}
                                            >
                                                Delete Review
                                            </Button>
                                        </Box>
                                    )}
                                </Box>
                            </CardContent>
                        </Card>
                    ))
                )}
            </Box>

            {/* Edit Review Dialog */}
            <Dialog open={editDialogOpen} onClose={handleCloseDialog} fullWidth maxWidth="sm">
                <DialogTitle>Edit Your Review</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Title"
                        type="text"
                        fullWidth
                        variant="outlined"
                        value={editTitle}
                        onChange={(e) => setEditTitle(e.target.value)}
                        sx={{ mb: 2 }}
                    />
                    <Typography component="legend">Rating</Typography>
                    <Rating
                        name="edit-rating"
                        value={editRating}
                        precision={0.5}
                        onChange={(e, newValue) => setEditRating(newValue)}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        margin="dense"
                        label="Review"
                        multiline
                        rows={4}
                        fullWidth
                        variant="outlined"
                        value={editBody}
                        onChange={(e) => setEditBody(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                    <Button onClick={handleSaveEdit} variant="contained" color="primary">Save</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default ServiceReviewsList;