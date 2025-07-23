import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AccountBalances = () => {
    const [accounts, setAccounts] = useState({});

    const fetchAccounts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/accounts');
            setAccounts(response.data);
        } catch (error) {
            console.error("Failed to fetch accounts:", error);
        }
    };

    useEffect(() => {
        fetchAccounts();
    }, []);

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px' }}>
            <h2>Account Balances</h2>
            <button onClick={fetchAccounts} style={{ marginBottom: '15px' }}>Refresh Balances</button>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>Account ID</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>Balance</th>
                    </tr>
                </thead>
                <tbody>
                    {Object.entries(accounts).map(([accountId, balance]) => (
                        <tr key={accountId}>
                            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{accountId}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px' }}>£{parseFloat(balance).toFixed(2)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default AccountBalances;