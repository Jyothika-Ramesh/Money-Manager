import React, { useEffect, useState } from 'react';
import axios from '../services/AxiosConfig';
// import ExpenseModal from '../components/ExpenseModal';
import ExpenseChart from '../components/ExpenseChart';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const MemberDashboard = () => {
  const [expenses, setExpenses] = useState([]);
  // const [showModal, setShowModal] = useState(false);
  const [familyname, setFamilyname] = useState('');
  const [month, setMonth] = useState('2025-11');
  const [income, setIncome] = useState(null);
  const navigate = useNavigate();

   const handleSignOut = () => {
    localStorage.clear();
    navigate('/');
  };

  const fetchExpenses = async () => {
    try {
      const res = await axios.get(`/api/expenses/all?familyname=${familyname}`);
  setExpenses(Array.isArray(res.data) ? res.data.filter(e => e && e.date && e.date.startsWith(month)) : []);
   setFamilyname(res.data.length > 0 ? res.data[0].familyname : familyname);
    } catch {
      toast.error('Failed to fetch expenses');
    }
  };


const fetchIncome = async () => {
    try {  
      const res = await axios.get(`/api/income/all?familyname=${familyname}`);
      const match = Array.isArray(res.data) ? res.data.find(i => i && i.month === month) : null;
      setIncome(match || null);
      console.log('Fetched income:', match);
    //  if (match && match.familyname) setFamilyname(match.familyname);
    } catch {
      console.error('Failed to fetch income');
      toast.error('Failed to fetch income');
    }
  };
  // const handleAddExpense = async (form) => {
  //   try {
  //     await axios.post('/api/expenses', form);
  //     toast.success('Expense added');
  //     setShowModal(false);
  //     fetchExpenses();
  //   } catch {
  //     toast.error('Failed to add expense');
  //   }
  // };

  useEffect(() => {
    fetchExpenses();
    fetchIncome();
        // if (!familyname) return;
  }, [familyname, month]);

  return (
    <>
    <div className="d-flex justify-content-between p-3 bg-dark fixed-header">
        <h2 className="mb-3 text-light">{familyname}'s family</h2>
        <button className="btn btn-outline-light" onClick={handleSignOut}>Sign Out</button>
      </div>
      <br/>
    <div className="container mt-4" >
      {/* <h2 className="mb-3">Your Family Expenses for {month}</h2> */}

      <div className="col-md-4" style={{marginTop:'100px'}}>
          <label>Month</label>
          <input type="month" className="form-control" value={month} onChange={e => setMonth(e.target.value)} />
        </div>
        <br/>
        <div className="col-md-4">
           {income && <p><strong>Current Income:</strong> <h3>₹{income.amount}</h3></p>}
        </div>

 
  {/* ExpenseModal removed for member dashboard */}

      <h4 className="mt-4">Expense List</h4>
      <ul className="list-group">
        {Array.isArray(expenses) && expenses.length > 0 ? (
          expenses.map(e => (
            <li key={e.id} className="list-group-item">
              <strong>{e.category}</strong>: ₹{e.amountSpent} on {e.date}
              {e.note && <div className="text-muted">Note: {e.note}</div>}
            </li>
          ))
        ) : (
          <li className="list-group-item text-muted">No expenses found</li>
        )}
      </ul>
        <br/>
      <div>
            total expenses: ₹{expenses.reduce((total, e) => total + (e.amountSpent || 0), 0)} <br/>
            remaining budget: ₹{income ? income.amount - expenses.reduce((total, e) => total + (e.amountSpent || 0), 0) : 'N/A'}
        </div>

         <ExpenseChart expenses={expenses} />
    </div>
    </>
  );
};

export default MemberDashboard;
