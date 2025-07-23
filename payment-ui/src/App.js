import React from 'react';
import './App.css';
import PaymentForm from './components/PaymentForm';
import LiveTransactionFeed from './components/LiveTransactionFeed';

function App() {
  return (
    <div style={{ padding: '20px' }}>
      <h1>High-Speed Payment Processor Dashboard</h1>
      <hr />
      <PaymentForm />
      <LiveTransactionFeed />
    </div>
  );
}

export default App;