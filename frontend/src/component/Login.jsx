import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {
    TextField,
    Button,
    Box,
    Typography,
    Container,
    Paper
} from '@mui/material';

const Login = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/api/auth/login', formData);
            
            if (response.data.startsWith('Error')) {
                setError(response.data);
            } else {
                const userRole = response.data.split('Role: ')[1];
                sessionStorage.setItem('userRole', userRole);
                navigate(userRole === 'ADMIN' ? '/admin' : '/dashboard');
            }
        } catch (err) {
            setError(err.response?.data || 'Invalid credentials');
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
                <Typography component="h1" variant="h5" textAlign="center">
                    Login
                </Typography>
                <Box component="form" onSubmit={handleLogin} sx={{ mt: 3 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                        value={formData.email}
                        onChange={handleChange}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                    {error && (
                        <Typography color="error" sx={{ mt: 2 }}>
                            {error}
                        </Typography>
                    )}
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Login
                    </Button>
                    <Button
                        fullWidth
                        variant="text"
                        onClick={() => navigate('/register')}
                    >
                        Don't have an account? Register
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
};


export default Login;
