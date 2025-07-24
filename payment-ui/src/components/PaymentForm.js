import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Box, TextField, Button, Typography, Paper, Select, MenuItem, FormControl, InputLabel } from '@mui/material';

const PaymentForm = ({ accounts, onPaymentSuccess }) => {
    const [sourceAccountId, setSourceAccountId] = useState('');
    const [destinationAccountId, setDestinationAccountId] = useState('');
    const [amount, setAmount] = useState('');
    
    useEffect(() => {
        if (accounts.length > 0 && !sourceAccountId) { setSourceAccountId(accounts[0]); }
        if (accounts.length > 1 && !destinationAccountId) { setDestinationAccountId(accounts[1]); }
    }, [accounts, sourceAccountId, destinationAccountId]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const paymentRequest = { sourceAccountId, destinationAccountId, amount };
        try {
            await axios.post('http://localhost:8080/api/payments', paymentRequest);
            onPaymentSuccess();
        } catch (error) { alert(`Error: ${error.response ? error.response.data.error : error.message}`); }
    };

    return (
        <Paper variant="outlined" sx={{ p: 2, backgroundColor: 'rgba(255, 255, 255, 0.05)', mb: 3 }}>
            <Typography variant="h6" gutterBottom>💸 Submit Payment</Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate>
                <FormControl fullWidth margin="normal" size="small">
                    <InputLabel id="source-account-label">From Account</InputLabel>
                    <Select labelId="source-account-label" value={sourceAccountId} label="From Account" onChange={(e) => setSourceAccountId(e.target.value)} required>
                        {accounts.map(acc => <MenuItem key={acc} value={acc}>{acc}</MenuItem>)}
                    </Select>
                </FormControl>
                <FormControl fullWidth margin="normal" size="small">
                    <InputLabel id="destination-account-label">To Account</InputLabel>
                    <Select labelId="destination-account-label" value={destinationAccountId} label="To Account" onChange={(e) => setDestinationAccountId(e.target.value)} required>
                        {accounts.map(acc => <MenuItem key={acc} value={acc}>{acc}</MenuItem>)}
                    </Select>
                </FormControl>
                <TextField label="Amount" variant="outlined" type="number" fullWidth margin="normal" size="small" value={amount} onChange={(e) => setAmount(e.target.value)} required InputProps={{ inputProps: { step: "0.01" } }} />
                <Button type="submit" variant="contained" color="primary" sx={{ mt: 1 }}>Send Payment</Button>
            </Box>
        </Paper>
    );
};

export default PaymentForm;