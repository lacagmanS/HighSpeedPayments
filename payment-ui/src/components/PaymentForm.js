import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PaymentForm = ({ accounts, onPaymentSuccess }) => {
    const [sourceAccountId, setSourceAccountId] = useState('');
    const [destinationAccountId, setDestinationAccountId] = useState('');
    const [amount, setAmount] = useState('');

    useEffect(() => {
        if (accounts.length > 0) {
            setSourceAccountId(accounts[0]);
        }
        if (accounts.length > 1) {
            setDestinationAccountId(accounts[1]);
        }
    }, [accounts]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const paymentRequest = { sourceAccountId, destinationAccountId, amount };

        try {
            const response = await axios.post('http://localhost:8080/api/payments', paymentRequest);
            alert(`Success: ${response.data.message}\nTransaction ID: ${response.data.transactionId}`);
            onPaymentSuccess();
        } catch (error) {
            alert(`Error: ${error.response ? error.response.data.error : error.message}`);
        }
    };

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px' }}>
            <h2>Submit a Payment</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Source Account: </label>
                    <select value={sourceAccountId} onChange={(e) => setSourceAccountId(e.target.value)} required>
                        {accounts.map(acc => <option key={acc} value={acc}>{acc}</option>)}
                    </select>
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Destination Account: </label>
                    <select value={destinationAccountId} onChange={(e) => setDestinationAccountId(e.target.value)} required>
                        {accounts.map(acc => <option key={acc} value={acc}>{acc}</option>)}
                    </select>
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Amount: </label>
                    <input type="number" step="0.01" value={amount} onChange={(e) => setAmount(e.target.value)} required />
                </div>
                <button type="submit">Submit Payment</button>
            </form>
        </div>
    );
};

export default PaymentForm;