import React, { useState } from 'react';
import axios from 'axios';

const AccountManagement = ({ onAccountCreated }) => {
    const [accountId, setAccountId] = useState('');
    const [balance, setBalance] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/accounts', { accountId, balance });
            alert(`Account '${accountId}' created successfully!`);
            setAccountId('');
            setBalance('');
            onAccountCreated();
        } catch (error) {
            alert(`Error creating account: ${error.message}`);
        }
    };

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px' }}>
            <h2>Account Management</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label>Account ID: </label>
                    <input type="text" value={accountId} onChange={(e) => setAccountId(e.target.value)} required />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label>Initial Balance: </label>
                    <input type="number" value={balance} onChange={(e) => setBalance(e.target.value)} required />
                </div>
                <button type="submit">Create Account</button>
            </form>
        </div>
    );
};

export default AccountManagement;