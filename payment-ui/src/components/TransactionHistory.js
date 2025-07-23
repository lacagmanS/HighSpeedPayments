import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TransactionHistory = ({ refreshKey }) => {
    const [history, setHistory] = useState([]);

    useEffect(() => {
        const fetchHistory = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/payments/history');
                setHistory(response.data);
            } catch (error) {
                console.error("Failed to fetch history:", error);
            }
        };
        fetchHistory();
    }, [refreshKey]);

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px' }}>
            <h2>Transaction History</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>ID</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>From</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>To</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>Amount</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>Status</th>
                        <th style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'left' }}>Signature</th>
                    </tr>
                </thead>
                <tbody>
                    {history.map((tx) => (
                        <tr key={tx.transactionId}>
                            <td style={{ border: '1px solid #ddd', padding: '8px', fontSize: '12px', wordBreak: 'break-all' }}>{tx.transactionId}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{tx.sourceAccountId}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{tx.destinationAccountId}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px' }}>£{parseFloat(tx.amount).toFixed(2)}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px', color: tx.status === 'COMPLETED' ? 'green' : 'red' }}>{tx.status}</td>
                            <td style={{ border: '1px solid #ddd', padding: '8px', fontSize: '10px', wordBreak: 'break-all', fontFamily: 'monospace' }}>{tx.signature}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default TransactionHistory;