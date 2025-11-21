import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import './LoginPage.css';

const LoginPage = () => {
  const [form, setForm] = useState({ mail: '', pswd: '' });
  const navigate = useNavigate();

  useEffect(() => {
    // add a class to body so the background styles apply only on this page
    document.body.classList.add('login-body');
    return () => {
      document.body.classList.remove('login-body');
    };
  }, []);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const res = await axios.post('/api/auth/login', form);
      localStorage.setItem('token', res.data.token);
      localStorage.setItem('role', res.data.role);
      toast.success('Login successful');
      navigate(res.data.role === 'admin' ? '/admin' : '/member');
    } catch {
      toast.error('Invalid credentials');
    }
  };

  return (
    <div className="container" id='mainDiv' style={{marginTop : '276px', marginRight:'680px'}}>
          <h3 className="mt-3 text-center">Login</h3>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <input type="email" name="mail" id='input' className="form-control" style={{outline:'none'}} placeholder="Email" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <input type="password" id='input' name="pswd" className="form-control" placeholder="Password" onChange={handleChange} required />
            </div>
            <button type="submit" id='btn' className="btn btn-primary w-100 mt-3">Login</button>
           <div className="d-flex justify-content-center mt-3 mb-3">
            don't have an account?<Link to="/register" variant="primary" className="w-25">Sign up</Link>
            </div>
          </form>
    </div>
  );
};

export default LoginPage;
