import React from 'react';
import { Box, Typography, Button, Container } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const navigate = useNavigate();
    
    const handleLogout = () => {
        sessionStorage.removeItem('userRole');
        navigate('/login');
    };

    return (
        <Container>
            <Box sx={{ mt: 4 }}>
                <Typography variant="h4" gutterBottom>
                    User Dashboard
                </Typography>
                <Typography variant="body1" paragraph>
                    Welcome to your dashboard! You are logged in as a regular user.
                </Typography>
                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={handleLogout}
                >
                    Logout
                </Button>
            </Box>
        </Container>
    );
};

export default Dashboard;