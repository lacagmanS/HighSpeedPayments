import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Paper, Typography, Box, Tabs, Tab } from '@mui/material';
import TransactionHistory from './TransactionHistory';

const LiveTransactionFeed = ({ refreshKey }) => {
    const [logs, setLogs] = useState([]);
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            onConnect: () => {
                client.subscribe('/topic/logs', message => {
                    setLogs(prevLogs => [`[${new Date().toLocaleTimeString()}] ${message.body}`, ...prevLogs].slice(0, 100));
                });
            },
            reconnectDelay: 5000,
        });
        client.activate();
        return () => { client.deactivate(); };
    }, []);

    return (
        <Paper variant="outlined" sx={{ p: 0, backgroundColor: 'rgba(255, 255, 255, 0.05)' }}>
             <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange}>
                    <Tab label="Live Feed" />
                    <Tab label="Transaction History" />
                </Tabs>
            </Box>
            {value === 0 && (
                <Box sx={{ p:2 }}>
                    <Typography variant="h6" gutterBottom>📡 Live Transaction Feed</Typography>
                    <Box sx={{ height: '400px', overflowY: 'scroll', backgroundColor: '#212121', p: 2, borderRadius: 1, fontFamily: 'monospace', fontSize: '14px' }}>
                        {logs.map((log, index) => (
                            <div key={index} style={{ color: '#00e676' }}>{log}</div>
                        ))}
                    </Box>
                </Box>
            )}
            {value === 1 && (
                <Box sx={{ p:2 }}>
                    <TransactionHistory refreshKey={refreshKey} />
                </Box>
            )}
        </Paper>
    );
};

export default LiveTransactionFeed;