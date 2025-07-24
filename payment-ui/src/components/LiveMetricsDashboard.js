import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Paper, Typography, Grid, Box } from '@mui/material';

const LiveMetricsDashboard = () => {
    const [metrics, setMetrics] = useState({ tps: 0, avgLatencyMicros: 0 });

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            onConnect: () => {
                client.subscribe('/topic/metrics', message => {
                    setMetrics(JSON.parse(message.body));
                });
            },
            reconnectDelay: 5000,
        });
        client.activate();
        return () => { client.deactivate(); };
    }, []);

    return (
        <Grid container spacing={3} sx={{ mb: 3 }}>
            <Grid item xs={6}>
                <Paper variant="outlined" sx={{ p: 2, textAlign: 'center', backgroundColor: 'rgba(255, 255, 255, 0.05)' }}>
                    <Typography variant="button" color="text.secondary">Transactions/Sec (TPS)</Typography>
                    <Typography variant="h4" sx={{ fontWeight: '600', color: '#66bb6a' }}>{metrics.tps}</Typography>
                </Paper>
            </Grid>
            <Grid item xs={6}>
                <Paper variant="outlined" sx={{ p: 2, textAlign: 'center', backgroundColor: 'rgba(255, 255, 255, 0.05)' }}>
                    <Typography variant="button" color="text.secondary">Avg. Latency (µs)</Typography>
                    <Typography variant="h4" sx={{ fontWeight: '600', color: '#42a5f5' }}>{metrics.avgLatencyMicros}</Typography>
                </Paper>
            </Grid>
        </Grid>
    );
};

export default LiveMetricsDashboard;