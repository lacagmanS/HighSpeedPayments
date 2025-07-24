import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Typography, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';

const AccountBalances = ({ refreshKey }) => {
    const [accounts, setAccounts] = useState({});
    const fetchAccounts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/accounts');
            setAccounts(response.data);
        } catch (error) { console.error("Failed to fetch accounts:", error); }
    };

    useEffect(() => {
        fetchAccounts();
    }, [refreshKey]);

    return (
        <Paper variant="outlined" sx={{ p: 2, backgroundColor: 'rgba(255, 255, 255, 0.05)', mb: 3 }}>
            <Typography variant="h6" gutterBottom>🏦 Account Balances</Typography>
            <TableContainer>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ fontWeight: '600', borderBottom: '1px solid rgba(255, 255, 255, 0.2)' }}>Account ID</TableCell>
                            <TableCell align="right" sx={{ fontWeight: '600', borderBottom: '1px solid rgba(255, 255, 255, 0.2)' }}>Balance</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {Object.entries(accounts).map(([accountId, balance]) => (
                            <TableRow key={accountId} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                <TableCell>{accountId}</TableCell>
                                <TableCell align="right">£{parseFloat(balance).toFixed(2)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    );
};

export default AccountBalances;