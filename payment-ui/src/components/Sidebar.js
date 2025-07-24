import React from 'react';
import { Box, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Typography, Divider } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PaymentIcon from '@mui/icons-material/Payment';
import HistoryIcon from '@mui/icons-material/History';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';

const Sidebar = () => {
    const scrollTo = (id) => {
        document.getElementById(id)?.scrollIntoView({ behavior: 'smooth' });
    };

    return (
        <Box sx={{
            width: 240,
            height: '100vh',
            bgcolor: '#202123',
            borderRight: '1px solid rgba(255, 255, 255, 0.12)',
            display: 'flex',
            flexDirection: 'column',
            position: 'fixed'
        }}>
            <Box sx={{ p: 2, display: 'flex', alignItems: 'center', gap: 1 }}>
                <AccountBalanceWalletIcon sx={{ color: '#42a5f5' }} />
                <Typography variant="h6" sx={{ fontWeight: 'bold' }}>Payments</Typography>
            </Box>
            <Divider sx={{ borderColor: 'rgba(255, 255, 255, 0.12)' }} />
            <List>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => scrollTo('dashboard-top')} sx={{ borderRadius: 2, m:1 }}>
                        <ListItemIcon sx={{ minWidth: 32, color: 'white' }}><DashboardIcon /></ListItemIcon>
                        <ListItemText primary="Dashboard" />
                    </ListItemButton>
                </ListItem>
                 <ListItem disablePadding>
                    <ListItemButton onClick={() => scrollTo('submit-payment')} sx={{ borderRadius: 2, m:1 }}>
                        <ListItemIcon sx={{ minWidth: 32, color: 'white' }}><PaymentIcon /></ListItemIcon>
                        <ListItemText primary="Submit Payment" />
                    </ListItemButton>
                </ListItem>
                 <ListItem disablePadding>
                    <ListItemButton onClick={() => scrollTo('transaction-history')} sx={{ borderRadius: 2, m:1 }}>
                        <ListItemIcon sx={{ minWidth: 32, color: 'white' }}><HistoryIcon /></ListItemIcon>
                        <ListItemText primary="History" />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    );
};

export default Sidebar;