import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const LiveTransactionFeed = () => {
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            onConnect: () => {
                client.subscribe('/topic/logs', message => {
                    setLogs(prevLogs => [message.body, ...prevLogs]);
                });
            },
        });

        client.activate();

        return () => {
            client.deactivate();
        };
    }, []);

    return (
        <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginTop: '20px', height: '300px', overflowY: 'scroll', backgroundColor: '#f5f5f5' }}>
            <h2>Live Transaction Feed</h2>
            <div style={{ fontFamily: 'monospace', fontSize: '14px' }}>
                {logs.map((log, index) => (
                    <p key={index} style={{ margin: '5px 0', borderBottom: '1px solid #eee', paddingBottom: '5px' }}>{log}</p>
                ))}
            </div>
        </div>
    );
};

export default LiveTransactionFeed;