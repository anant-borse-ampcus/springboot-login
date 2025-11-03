import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Select, MenuItem, FormControl, InputLabel, Box, Typography, Container, Paper } from '@mui/material';

const Register = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        role: 'USER'
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/api/auth/signup', formData);
            
            if (response.data.startsWith('Error')) {
                setError(response.data);
            } else {
                alert('Registration successful!');
                navigate('/login');
            }
        } catch (err) {
            setError(err.response?.data || 'Error during registration');
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
                <Typography component="h1" variant="h5" textAlign="center">
                    Register
                </Typography>
                <Box component="form" onSubmit={handleRegister} sx={{ mt: 3 }}>
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
                        autoComplete="new-password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="role-label">Role</InputLabel>
                        <Select
                            labelId="role-label"
                            id="role"
                            name="role"
                            value={formData.role}
                            label="Role"
                            onChange={handleChange}
                        >
                            <MenuItem value="USER">User</MenuItem>
                            <MenuItem value="ADMIN">Admin</MenuItem>
                        </Select>
                    </FormControl>
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
                        Register
                    </Button>
                    <Button
                        fullWidth
                        variant="text"
                        onClick={() => navigate('/login')}
                    >
                        Already have an account? Login
                    </Button>
                </Box>
            </Paper>
        </Container>
    );
};

export default Register;
