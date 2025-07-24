import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';
import PaymentForm from './components/PaymentForm';
import LiveTransactionFeed from './components/LiveTransactionFeed';
import AccountBalances from './components/AccountBalances';
import AccountManagement from './components/AccountManagement';
import LiveMetricsGraph from './components/LiveMetricsGraph';
import Sidebar from './components/Sidebar';
import { Box, Typography, Paper, Button, Grid, ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import TransactionHistory from './components/TransactionHistory';

const darkTheme = createTheme({
    palette: {
        mode: 'dark',
        primary: { main: '#42a5f5' },
        secondary: { main: '#ff7043' },
        background: { default: '#202123', paper: '#2d2d2d' },
    },
    typography: { fontFamily: 'Inter, sans-serif' },
});

function App() {
  const [accounts, setAccounts] = useState({});
  const [refreshKey, setRefreshKey] = useState(0);

  const handleRefresh = () => { setRefreshKey(oldKey => oldKey + 1) };

  const fetchAccounts = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/accounts');
      setAccounts(response.data);
    } catch (error) { console.error("Failed to fetch accounts:", error); }
  };

  const handleChaosClick = () => {
    const accountIds = Object.keys(accounts);
    if (accountIds.length < 2) {
      alert("Please create at least two accounts to run the chaos test.");
      return;
    }
    const promises = [];
    for (let i = 0; i < 500; i++) {
      let source = accountIds[Math.floor(Math.random() * accountIds.length)];
      let destination = accountIds[Math.floor(Math.random() * accountIds.length)];
      while (source === destination) {
        destination = accountIds[Math.floor(Math.random() * accountIds.length)];
      }
      const amount = (Math.random() * 10 + 1).toFixed(2);
      
      const paymentRequest = { sourceAccountId: source, destinationAccountId: destination, amount };
      promises.push(axios.post('http://localhost:8080/api/payments', paymentRequest).catch(err => console.error(err)));
    }
    Promise.all(promises).then(() => { setTimeout(handleRefresh, 1500); });
  };
  
  useEffect(() => {
      fetchAccounts();
  }, [refreshKey]);

  return (
    <ThemeProvider theme={darkTheme}>
        <CssBaseline />
        <Box sx={{ display: 'flex' }}>
            <Sidebar />
            <Box component="main" sx={{ flexGrow: 1, p: 3, ml: '240px' }}>
                <Box id="dashboard-top">
                    <Typography variant="h4" component="h1" sx={{ fontWeight: '600' }}>Dashboard</Typography>
                    <Typography color="text.secondary" sx={{ mb: 3 }}>Real-time monitoring of an LMAX Disruptor-powered transaction engine.</Typography>
                </Box>
                
                <Grid container spacing={3}>
                    <Grid item xs={12} lg={8}>
                        <LiveMetricsGraph />
                        <LiveTransactionFeed refreshKey={refreshKey} />
                    </Grid>
                    <Grid item xs={12} lg={4}>
                         <Box id="submit-payment">
                            <AccountManagement onAccountCreated={handleRefresh} />
                            <PaymentForm accounts={Object.keys(accounts)} onPaymentSuccess={handleRefresh} />
                         </Box>
                         <Paper variant="outlined" sx={{ p: 2, backgroundColor: 'rgba(255, 255, 255, 0.05)' }}>
                            <Typography variant="h6" gutterBottom>🚀 Performance Test</Typography>
                            <Button onClick={handleChaosClick} variant="contained" color="secondary" fullWidth>Launch 500 Test Payments</Button>
                        </Paper>
                        <Box mt={3}>
                            <AccountBalances refreshKey={refreshKey} />
                        </Box>
                    </Grid>
                </Grid>
                
                
            </Box>
        </Box>
    </ThemeProvider>
  );
}

export default App;