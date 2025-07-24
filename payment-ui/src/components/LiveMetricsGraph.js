import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Paper, Typography, Box } from '@mui/material';
import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';

const LiveMetricsGraph = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            onConnect: () => {
                client.subscribe('/topic/metrics', message => {
                    const newMetrics = JSON.parse(message.body);
                    const timestamp = new Date().toLocaleTimeString();
                    setData(prevData => [...prevData, { name: timestamp, tps: newMetrics.tps }].slice(-30));
                });
            },
            reconnectDelay: 5000,
        });
        client.activate();
        return () => { client.deactivate(); };
    }, []);

    return (
        <Paper variant="outlined" sx={{ p: 2, height: 300, backgroundColor: 'rgba(255, 255, 255, 0.05)', mb: 3 }}>
            <Typography variant="h6" gutterBottom>Live Throughput (TPS)</Typography>
            <ResponsiveContainer width="100%" height="90%">
                <LineChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(255, 255, 255, 0.2)" />
                    <XAxis dataKey="name" stroke="rgba(255, 255, 255, 0.7)" />
                    <YAxis stroke="rgba(255, 255, 255, 0.7)" />
                    <Tooltip contentStyle={{ backgroundColor: '#333', border: '1px solid #555' }} />
                    <Line type="monotone" dataKey="tps" stroke="#ff7043" strokeWidth={2} dot={false} isAnimationActive={false} />
                </LineChart>
            </ResponsiveContainer>
        </Paper>
    );
};

export default LiveMetricsGraph;