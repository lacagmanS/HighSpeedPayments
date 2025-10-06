import React, { useState } from 'react';
import { Box, TextField, Button, Typography, Paper } from '@mui/material';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        alert('Login functionality to be implemented.');
    };

    return (
        <Paper elevation={3} sx={{ padding: 3, maxWidth: 400, margin: '50px auto' }}>
            <Typography variant="h5" component="h1" gutterBottom>
                Login
            </Typography>
            <Box component="form" onSubmit={handleSubmit}>
                <TextField
                    label="Username"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <TextField
                    label="Password"
                    type="password"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
                    Login
                </Button>
            </Box>
        </Paper>
    );
};

export default LoginPage;