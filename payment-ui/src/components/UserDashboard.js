import React from 'react';
import { Box, Typography, Paper } from '@mui/material';

const UserDashboard = () => {
    return (
        <Paper elevation={3} sx={{ padding: 3, margin: '50px' }}>
            <Typography variant="h5" component="h1">
                Welcome to your Dashboard
            </Typography>
            <Typography sx={{ mt: 2 }}>
                Your personal account balances and transaction history will be displayed here.
            </Typography>
        </Paper>
    );
};

export default UserDashboard;