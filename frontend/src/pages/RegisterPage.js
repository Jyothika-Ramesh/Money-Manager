import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Link, useNavigate } from 'react-router-dom';
import './LoginPage.css';

const RegisterPage = () => {
  const [form, setForm] = useState({
    name: '',
    mail: '',
    pswd: '',
    role: 'member',
    familyname: ''
  });

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
      await axios.post('/api/auth/register', form);
      toast.success('Registration successful');
      navigate('/');
    } catch {
      toast.error('Registration failed');
    }
  };

  return (
    <div className="container" id='mainDiv' style={{marginTop : '150px', marginRight:'680px'}}>
          <h3 className="mb-4 text-center mt-3">Register</h3>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <input name="name" id='input' className="form-control" placeholder="Full Name" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <input name="mail" id='input' type="email" className="form-control" placeholder="Email" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <input name="pswd" id='input' type="password" className="form-control" placeholder="Password" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <select name="role" className="form-select" onChange={handleChange}>
                <option value="member">Member</option>
                <option value="admin">Admin</option>
              </select>
            </div>
            <div className="mb-3">
             <select name="familyname" className="form-select" onChange={handleChange}>
                <option value="TheSharmas">TheSharmas</option>
                <option value="TheRamesh">TheRamesh</option>
              </select>
            </div>
            <button type="submit" id='btn' className="btn btn-primary w-100 mt-3">Register</button>
            <div className="d-flex justify-content-center mt-3 mb-3">
                already have an account?<Link to="/" variant="primary" className="w-25">Sign in</Link>
            </div>
          </form>
    </div>
  );
};

export default RegisterPage;
