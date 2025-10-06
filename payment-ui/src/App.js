import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline, AppBar, Toolbar, Typography, Button } from '@mui/material';

// Import all your "page" components
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import UserDashboard from './components/UserDashboard';
import AdminDashboard from './components/AdminDashboard'; // Your original dashboard

const darkTheme = createTheme({
    palette: {
        mode: 'dark',
        primary: { main: '#42a5f5' },
        secondary: { main: '#ff7043'},
        background: { default: '#121212', paper: '#1e1e1e' },
    },
    typography: { fontFamily: 'Inter, sans-serif' },
});

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <Router>
        <AppBar position="static" sx={{ mb: 4, bgcolor: '#202123' }}>
          <Toolbar>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              Payment Platform
            </Typography>
            <Button color="inherit" component={Link} to="/register">Register</Button>
            <Button color="inherit" component={Link} to="/login">Login</Button>
            <Button color="inherit" component={Link} to="/dashboard">User Dashboard</Button>
            <Button color="inherit" component={Link} to="/admin">Admin Dashboard</Button>
          </Toolbar>
        </AppBar>

        <Routes>
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard" element={<UserDashboard />} />
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/" element={<RegisterPage />} /> {/* Default page */}
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
