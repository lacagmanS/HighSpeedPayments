import React, { useState } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography, Paper } from '@mui/material';

const AccountManagement = ({ onAccountCreated }) => {
    const [accountId, setAccountId] = useState('');
    const [balance, setBalance] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/accounts', { accountId, balance });
            setAccountId('');
            setBalance('');
            onAccountCreated();
        } catch (error) { alert(`Error creating account: ${error.message}`); }
    };

    return (
        <Paper variant="outlined" sx={{ p: 2, backgroundColor: 'rgba(255, 255, 255, 0.05)', mb: 3 }}>
            <Typography variant="h6" gutterBottom>➕ Create Account</Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate>
                <TextField label="Account ID" variant="outlined" fullWidth margin="normal" size="small" value={accountId} onChange={(e) => setAccountId(e.target.value)} required />
                <TextField label="Initial Balance" variant="outlined" type="number" fullWidth margin="normal" size="small" value={balance} onChange={(e) => setBalance(e.target.value)} required />
                <Button type="submit" variant="contained" color="primary" sx={{ mt: 1 }}>Create Account</Button>
            </Box>
        </Paper>
    );
};

export default AccountManagement;