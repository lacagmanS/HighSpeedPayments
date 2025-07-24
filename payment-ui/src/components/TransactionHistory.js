import React, { useState, useEffect } from 'react';
import axios from 'axios';

const TransactionHistory = ({ refreshKey }) => {
    const [history, setHistory] = useState([]);
    const [selectedTx, setSelectedTx] = useState(null);
    const [verificationStatus, setVerificationStatus] = useState(null);

    useEffect(() => {
        const fetchHistory = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/payments/history');
                const sortedHistory = response.data.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
                setHistory(sortedHistory);
            } catch (error) {
                console.error("Failed to fetch history:", error);
            }
        };
        fetchHistory();
    }, [refreshKey]);

    const handleVerify = async (tx) => {
        try {
            const response = await axios.post('http://localhost:8080/api/payments/verify', tx);
            setVerificationStatus(response.data.isValid ? '✅ Verified Authentic' : '❌ Verification FAILED');
        } catch (error) {
            setVerificationStatus('Error during verification');
        }
    };

    const closeModal = () => {
        setSelectedTx(null);
        setVerificationStatus(null);
    };

    return (
        <>
            {selectedTx && (
                <div style={modalOverlayStyle} onClick={closeModal}>
                    <div style={modalContentStyle} onClick={e => e.stopPropagation()}>
                        <h2>Transaction Invoice</h2>
                        <p><strong>ID:</strong> {selectedTx.transactionId}</p>
                        <p><strong>From:</strong> {selectedTx.sourceAccountId}</p>
                        <p><strong>To:</strong> {selectedTx.destinationAccountId}</p>
                        <p><strong>Amount:</strong> £{parseFloat(selectedTx.amount).toFixed(2)}</p>
                        <p><strong>Status:</strong> {selectedTx.status}</p>
                        <p><strong>Timestamp:</strong> {new Date(selectedTx.timestamp).toLocaleString()}</p>
                        <p style={{fontFamily: 'monospace', wordBreak: 'break-all'}}><strong>Signature:</strong> {selectedTx.signature}</p>
                        <button onClick={() => handleVerify(selectedTx)}>Verify Authenticity</button>
                        {verificationStatus && <p><strong>Status: {verificationStatus}</strong></p>}
                        <button onClick={closeModal} style={{marginTop: '20px'}}>Close</button>
                    </div>
                </div>
            )}

            <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px' }}>
                <h2>Transaction History (Click a row for details)</h2>
                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            <th style={thStyle}>ID</th>
                            <th style={thStyle}>From</th>
                            <th style={thStyle}>To</th>
                            <th style={thStyle}>Amount</th>
                            <th style={thStyle}>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {history.map((tx) => (
                            <tr key={tx.transactionId} onClick={() => setSelectedTx(tx)} style={trStyle}>
                                <td style={tdStyle}>{tx.transactionId.substring(0, 8)}...</td>
                                <td style={tdStyle}>{tx.sourceAccountId}</td>
                                <td style={tdStyle}>{tx.destinationAccountId}</td>
                                <td style={tdStyle}>£{parseFloat(tx.amount).toFixed(2)}</td>
                                <td style={{...tdStyle, color: tx.status === 'COMPLETED' ? 'green' : 'red' }}>{tx.status}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    );
};

const thStyle = { border: '1px solid #ddd', padding: '8px', textAlign: 'left', backgroundColor: '#f2f2f2' };
const tdStyle = { border: '1px solid #ddd', padding: '8px', fontSize: '14px', wordBreak: 'break-all' };
const trStyle = { cursor: 'pointer' };

const modalOverlayStyle = {
    position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.7)', display: 'flex',
    alignItems: 'center', justifyContent: 'center'
};

const modalContentStyle = {
    backgroundColor: '#fff', padding: '20px', borderRadius: '8px',
    width: '500px', maxHeight: '90vh', overflowY: 'auto'
};

export default TransactionHistory;