import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';
import PaymentForm from './components/PaymentForm';
import LiveTransactionFeed from './components/LiveTransactionFeed';
import AccountBalances from './components/AccountBalances';
import AccountManagement from './components/AccountManagement';
import LiveMetricsDashboard from './components/LiveMetricsDashboard';
import TransactionHistory from './components/TransactionHistory';

function App() {
  const [accounts, setAccounts] = useState({});
  const [refreshKey, setRefreshKey] = useState(0);

  const handleRefresh = () => {
    setRefreshKey(oldKey => oldKey + 1)
  };

  const fetchAccounts = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/accounts');
      setAccounts(response.data);
    } catch (error) {
      console.error("Failed to fetch accounts:", error);
    }
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
    Promise.all(promises).then(() => {
        setTimeout(handleRefresh, 1000);
    });
  };
  
  useEffect(() => {
      fetchAccounts();
  }, [refreshKey]);

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
      <h1>High-Speed Payment Processor Dashboard</h1>
      <LiveMetricsDashboard />
      <hr />
      <div style={{ display: 'flex', gap: '20px' }}>
        <div style={{ flex: 1 }}>
          <PaymentForm accounts={Object.keys(accounts)} onPaymentSuccess={handleRefresh} />
          <AccountManagement onAccountCreated={handleRefresh} />
          <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px' }}>
            <h2>🚀 Performance Test</h2>
            <button onClick={handleChaosClick}>Launch 500 Test Payments</button>
          </div>
        </div>
        <div style={{ flex: 1 }}>
          <AccountBalances key={refreshKey} />
        </div>
      </div>
      <LiveTransactionFeed />
      <TransactionHistory refreshKey={refreshKey} />
    </div>
  );
}

export default App;