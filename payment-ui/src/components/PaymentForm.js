import React, { useState } from 'react';
import axios from 'axios';

const PaymentForm = () => {
    const [sourceAccountId, setSourceAccountId] = useState('');
    const [destinationAccountId, setDestinationAccountId] = useState('');
    const [amount, setAmount] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        const paymentRequest = {
            sourceAccountId,
            destinationAccountId,
            amount
        };

        try {
            const response = await axios.post('http://localhost:8080/api/payments', paymentRequest);
            alert(`Success: ${response.data.message}\nTransaction ID: ${response.data.transactionId}`);
        } catch (error) {
            alert(`Error: ${error.response ? error.response.data.error : error.message}`);
        }
    };

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px' }}>
            <h2>Submit a Payment</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Source Account ID: </label>
                    <input type="text" value={sourceAccountId} onChange={(e) => setSourceAccountId(e.target.value)} required />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Destination Account ID: </label>
                    <input type="text" value={destinationAccountId} onChange={(e) => setDestinationAccountId(e.target.value)} required />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Amount: </label>
                    <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
                </div>
                <button type="submit">Submit Payment</button>
            </form>
        </div>
    );
};

export default PaymentForm;