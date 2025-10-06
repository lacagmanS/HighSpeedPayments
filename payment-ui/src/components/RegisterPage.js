import React, { useState } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography, Paper } from '@mui/material';

const RegisterPage = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', { username, email, password });
            alert(response.data);
        } catch (error) {
            alert('Failed to register user.');
        }
    };

    return (
        <Paper elevation={3} sx={{ padding: 3, maxWidth: 400, margin: '50px auto' }}>
            <Typography variant="h5" component="h1" gutterBottom>
                Register
            </Typography>
            <Box component="form" onSubmit={handleSubmit}>
                <TextField label="Username" variant="outlined" fullWidth margin="normal" value={username} onChange={(e) => setUsername(e.target.value)} required />
                <TextField label="Email" type="email" variant="outlined" fullWidth margin="normal" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <TextField label="Password" type="password" variant="outlined" fullWidth margin="normal" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
                    Register
                </Button>
            </Box>
        </Paper>
    );
};

export default RegisterPage;