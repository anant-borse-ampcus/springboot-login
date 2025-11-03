import React, { useState, useEffect } from 'react';
import {
    Box,
    Typography,
    Button,
    Container,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    TextField,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Stack,
    Tooltip
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdminDashboard = () => {
    const [users, setUsers] = useState([]);
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('');
    const [enabled, setEnabled] = useState('');
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [totalPages, setTotalPages] = useState(1);
    const navigate = useNavigate();
    
    // On mount load all users (no filters) to preserve previous behaviour
    useEffect(() => {
        fetchUsers();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const fetchUsers = async (opts = {}) => {
        try {
            const hasFilters = (email && email.trim() !== '') || (role && role !== '') || (enabled !== '');

            // If no filters and no explicit pagination override, call endpoint without params
            const useSimpleCall = !hasFilters && opts.page === undefined && opts.size === undefined;

            let response;
            if (useSimpleCall) {
                response = await axios.get('http://localhost:8081/api/admin/search-users');
            } else {
                const params = {
                    page: opts.page !== undefined ? opts.page : page,
                    size: opts.size !== undefined ? opts.size : size,
                    ...(email ? { email } : {}),
                    ...(role ? { role } : {}),
                    ...(enabled === '' ? {} : { enabled: enabled === 'true' })
                };
                response = await axios.get('http://localhost:8081/api/admin/search-users', { params });
            }

            // Support both plain list (array) and Spring Page DTO
            if (Array.isArray(response.data)) {
                setUsers(response.data);
                setPage(0);
                setTotalPages(1);
            } else {
                setUsers(response.data.content || []);
                setPage(response.data.number ?? 0);
                setTotalPages(response.data.totalPages ?? 1);
            }
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    const handleDelete = async (userId) => {
        if (!userId) return;
        try {
            await axios.delete(`http://localhost:8081/api/admin/delete/${userId}`);
            fetchUsers();
        } catch (error) {
            console.error('Error deleting user:', error);
        }
    };

    // Toggle enabled state: call soft-delete (deactivate) or activate endpoint (activate)
    const toggleUserEnabled = async (user) => {
        if (!user?.id) return;
        try {
            if (user.enabled) {
                // currently active -> deactivate
                await axios.put(`http://localhost:8081/api/admin/soft-delete/${user.id}`);
            } else {
                // currently inactive -> activate (backend must provide this endpoint)
                await axios.put(`http://localhost:8081/api/admin/activate/${user.id}`);
            }
            fetchUsers();
        } catch (error) {
            console.error('Error toggling user enabled state:', error);
        }
    };

    const handleLogout = () => {
        sessionStorage.removeItem('userRole');
        navigate('/login');
    };

    return (
        <Container>
            <Box sx={{ mt: 4 }}>
                <Typography variant="h4" gutterBottom>
                    Admin Dashboard
                </Typography>
                
                <Paper sx={{ p: 2, mt: 2 }}>
                    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} alignItems="center">
                        <TextField
                            label="Email"
                            size="small"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <FormControl size="small" sx={{ minWidth: 120 }}>
                            <InputLabel id="role-label">Role</InputLabel>
                            <Select
                                labelId="role-label"
                                value={role}
                                label="Role"
                                onChange={(e) => setRole(e.target.value)}
                            >
                                <MenuItem value="">Any</MenuItem>
                                <MenuItem value="USER">USER</MenuItem>
                                <MenuItem value="ADMIN">ADMIN</MenuItem>
                            </Select>
                        </FormControl>
                        <FormControl size="small" sx={{ minWidth: 120 }}>
                            <InputLabel id="enabled-label">Enabled</InputLabel>
                            <Select
                                labelId="enabled-label"
                                value={enabled}
                                label="Enabled"
                                onChange={(e) => setEnabled(e.target.value)}
                            >
                                <MenuItem value="">Any</MenuItem>
                                <MenuItem value="true">Active</MenuItem>
                                <MenuItem value="false">Inactive</MenuItem>
                            </Select>
                        </FormControl>
                        <Button variant="contained" onClick={() => { setPage(0); fetchUsers({ page: 0 }); }}>
                            Search
                        </Button>
                        <Button variant="outlined" onClick={() => { setEmail(''); setRole(''); setEnabled(''); setPage(0); fetchUsers({ page: 0 }); }}>
                            Clear
                        </Button>
                    </Stack>

                    <TableContainer component={Paper} sx={{ mt: 3 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Email</TableCell>
                                <TableCell>Role</TableCell>
                                <TableCell>Status</TableCell>
                                <TableCell>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {users.map((user) => (
                                <TableRow key={user.email}>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.role}</TableCell>
                                        <TableCell>
                                            <Tooltip title={user.enabled ? 'Active' : 'Inactive'}>
                                                <Box
                                                    sx={{
                                                        width: 14,
                                                        height: 14,
                                                        borderRadius: '50%',
                                                        backgroundColor: user.enabled ? 'green' : 'red',
                                                        display: 'inline-block'
                                                    }}
                                                />
                                            </Tooltip>
                                        </TableCell>
                                    <TableCell>
                                            <Tooltip title={user.id ? (user.enabled ? 'Deactivate user' : 'Activate user') : 'Action unavailable: backend must return user id'}>
                                                <span>
                                                    <Button
                                                        size="small"
                                                        onClick={() => toggleUserEnabled(user)}
                                                        color={user.enabled ? "warning" : "success"}
                                                        disabled={!user.id}
                                                    >
                                                        {user.enabled ? 'Deactivate' : 'Activate'}
                                                    </Button>
                                                </span>
                                            </Tooltip>
                                            <Tooltip title={user.id ? 'Delete user' : 'Action unavailable: backend must return user id'}>
                                                <span>
                                                    <Button
                                                        size="small"
                                                        color="error"
                                                        onClick={() => handleDelete(user.id)}
                                                        disabled={!user.id}
                                                    >
                                                        Delete
                                                    </Button>
                                                </span>
                                            </Tooltip>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

                    {/* Pagination controls */}
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mt: 2 }}>
                        <Button
                            variant="outlined"
                            onClick={() => {
                                const prev = Math.max(0, page - 1);
                                fetchUsers({ page: prev });
                                setPage(prev);
                            }}
                            disabled={page <= 0}
                        >
                            Prev
                        </Button>
                        <Typography>Page {page + 1} of {totalPages}</Typography>
                        <Button
                            variant="outlined"
                            onClick={() => {
                                const next = Math.min(totalPages - 1, page + 1);
                                fetchUsers({ page: next });
                                setPage(next);
                            }}
                            disabled={page >= totalPages - 1}
                        >
                            Next
                        </Button>
                    </Box>

                </Paper>

                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={handleLogout}
                    sx={{ mt: 3 }}
                >
                    Logout
                </Button>
            </Box>
        </Container>
    );
};

export default AdminDashboard;