import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
   // console.log('Sending Authorization header:', config.headers.Authorization);
  } else {
    console.warn('No token found in localStorage');
  }
  return config;
});


export default axios;
// Redirect to login on 403 errors
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 403) {
        window.location = '/';
      console.warn('403 Forbidden - redirecting to login');
      
    }
    return Promise.reject(error);
  }
);
