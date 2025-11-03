import React from 'react';
import { Box, Typography, Button, Container } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Unauthorized = () => {
    const navigate = useNavigate();

    return (
        <Container>
            <Box sx={{ 
                mt: 8, 
                display: 'flex', 
                flexDirection: 'column', 
                alignItems: 'center' 
            }}>
                <Typography variant="h4" color="error" gutterBottom>
                    Access Denied
                </Typography>
                <Typography variant="body1" paragraph>
                    You do not have permission to access this page.
                </Typography>
                <Button 
                    variant="contained" 
                    onClick={() => navigate(-1)}
                >
                    Go Back
                </Button>
            </Box>
        </Container>
    );
};

export default Unauthorized;