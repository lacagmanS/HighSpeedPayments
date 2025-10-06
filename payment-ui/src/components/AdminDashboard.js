import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import PaymentForm from './PaymentForm';
import LiveTransactionFeed from './LiveTransactionFeed';
import AccountBalances from './AccountBalances';
import AccountManagement from './AccountManagement';
import LiveMetricsGraph from './LiveMetricsGraph';
import Sidebar from './Sidebar';
import { Box, Typography, Paper, Button, Grid } from '@mui/material';

function AdminDashboard() {
  const [accounts, setAccounts] = useState({});
  const [refreshKey, setRefreshKey] = useState(0);

  // Note: Refs are not needed for scrolling if the sidebar is for display only.
  // We keep them here in case we add that feature back later.
  const paymentRef = useRef(null);
  const historyRef = useRef(null);
  const dashboardRef = useRef(null);

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
    <Box sx={{ display: 'flex' }}>
        <Sidebar paymentRef={paymentRef} historyRef={historyRef} dashboardRef={dashboardRef} />
        <Box component="main" sx={{ flexGrow: 1, p: 3, marginLeft: '220px' }}>
            <Box ref={dashboardRef}>
                <Typography variant="h4" component="h1" sx={{ fontWeight: '600' }}>Admin Dashboard</Typography>
                <Typography color="text.secondary" sx={{ mb: 3 }}>Real-time monitoring of an LMAX Disruptor-powered transaction engine.</Typography>
            </Box>
            
            <LiveMetricsGraph />
            
            <Grid container spacing={3} sx={{ mt: 0 }}>
                <Grid item xs={12} lg={4}>
                     <Box ref={paymentRef}>
                        <AccountManagement onAccountCreated={handleRefresh} />
                        <PaymentForm accounts={Object.keys(accounts)} onPaymentSuccess={handleRefresh} />
                     </Box>
                     <Paper variant="outlined" sx={{ p: 2, backgroundColor: 'rgba(255, 255, 255, 0.05)' }}>
                        <Typography variant="h6" gutterBottom>🚀 Performance Test</Typography>
                        <Button onClick={handleChaosClick} variant="contained" color="secondary" fullWidth>Launch 500 Test Payments</Button>
                    </Paper>
                </Grid>
                <Grid item xs={12} lg={8}>
                    <AccountBalances refreshKey={refreshKey} />
                    <Box mt={3} ref={historyRef}>
                        <LiveTransactionFeed refreshKey={refreshKey}/>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    </Box>
  );
}

export default AdminDashboard;