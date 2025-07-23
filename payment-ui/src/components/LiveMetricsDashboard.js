import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const LiveMetricsDashboard = () => {
    const [metrics, setMetrics] = useState({ tps: 0, avgLatencyMicros: 0 });

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            onConnect: () => {
                client.subscribe('/topic/metrics', message => {
                    const newMetrics = JSON.parse(message.body);
                    setMetrics(newMetrics);
                });
            },
        });

        client.activate();

        return () => {
            client.deactivate();
        };
    }, []);

    return (
        <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
            <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', textAlign: 'center' }}>
                <h2>TPS</h2>
                <p style={{ fontSize: '2em', margin: 0 }}>{metrics.tps}</p>
            </div>
            <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', textAlign: 'center' }}>
                <h2>Avg. Latency (µs)</h2>
                <p style={{ fontSize: '2em', margin: 0 }}>{metrics.avgLatencyMicros}</p>
            </div>
        </div>
    );
};

export default LiveMetricsDashboard;