import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Modal, Box, Button, Chip } from '@mui/material';

const TransactionHistory = ({ refreshKey }) => {
    const [history, setHistory] = useState([]);
    const [selectedTx, setSelectedTx] = useState(null);
    const [verificationStatus, setVerificationStatus] = useState(null);

    useEffect(() => {
        const fetchHistory = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/payments/history');
                setHistory(response.data.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp)));
            } catch (error) { console.error("Failed to fetch history:", error); }
        };
        fetchHistory();
    }, [refreshKey]);
    
    const handleVerify = async (tx) => {
        try {
            const response = await axios.post(`http://localhost:8080/api/payments/${tx.transactionId}/verify`);
            setVerificationStatus(response.data.isValid ? '✅ Verified Authentic' : '❌ Verification FAILED');
        } catch (error) { setVerificationStatus('Error during verification'); }
    };
    
    const closeModal = () => {
        setSelectedTx(null);
        setVerificationStatus(null);
    };

    return (
        <>
            <Modal open={!!selectedTx} onClose={closeModal}>
                <Box sx={modalStyle}>
                    <Typography variant="h6" component="h2">🧾 Transaction Invoice</Typography>
                    <Typography sx={{ mt: 2 }}><strong>ID:</strong> {selectedTx?.transactionId}</Typography>
                    <Typography><strong>From:</strong> {selectedTx?.sourceAccountId}</Typography>
                    <Typography><strong>To:</strong> {selectedTx?.destinationAccountId}</Typography>
                    <Typography><strong>Amount:</strong> £{parseFloat(selectedTx?.amount).toFixed(2)}</Typography>
                    <Typography><strong>Status:</strong> {selectedTx?.status}</Typography>
                    <Typography><strong>Timestamp:</strong> {new Date(selectedTx?.timestamp).toLocaleString()}</Typography>
                    <Typography sx={{fontFamily: 'monospace', wordBreak: 'break-all', fontSize: '12px', mt:1}}><strong>Signature:</strong> {selectedTx?.signature}</Typography>
                    <Box sx={{ mt: 2 }}>
                        <Button onClick={() => handleVerify(selectedTx)} variant="contained">Verify Authenticity</Button>
                        {verificationStatus && <Typography sx={{ display: 'inline', marginLeft: 2, fontWeight: 'bold' }}>{verificationStatus}</Typography>}
                    </Box>
                </Box>
            </Modal>
            <TableContainer sx={{ maxHeight: 400 }}>
                <Table stickyHeader size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ fontWeight: '600' }}>ID</TableCell>
                            <TableCell sx={{ fontWeight: '600' }}>From</TableCell>
                            <TableCell sx={{ fontWeight: '600' }}>To</TableCell>
                            <TableCell sx={{ fontWeight: '600' }} align="right">Amount</TableCell>
                            <TableCell sx={{ fontWeight: '600' }} align="center">Status</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {history.map((tx) => (
                            <TableRow key={tx.transactionId} onClick={() => setSelectedTx(tx)} hover sx={{ cursor: 'pointer' }}>
                                <TableCell sx={{ fontFamily: 'monospace' }}>{tx.transactionId.substring(0, 8)}...</TableCell>
                                <TableCell>{tx.sourceAccountId}</TableCell>
                                <TableCell>{tx.destinationAccountId}</TableCell>
                                <TableCell align="right">£{parseFloat(tx.amount).toFixed(2)}</TableCell>
                                <TableCell align="center">
                                    <Chip label={tx.status} color={tx.status === 'COMPLETED' ? 'success' : 'error'} size="small" variant="outlined"/>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
};

const modalStyle = {
    position: 'absolute', top: '50%', left: '50%',
    transform: 'translate(-50%, -50%)', width: 600,
    bgcolor: 'background.paper', border: '1px solid #555',
    boxShadow: 24, p: 4, borderRadius: 2
};

export default TransactionHistory;