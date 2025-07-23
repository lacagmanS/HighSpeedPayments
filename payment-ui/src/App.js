import React from 'react';
import './App.css';
import PaymentForm from './components/PaymentForm';

function App() {
  return (
    <div style={{ padding: '20px' }}>
      <h1>High-Speed Payment Processor Dashboard</h1>
      <hr />
      <PaymentForm />
    </div>
  );
}

export default App;