import React, { useEffect, useState } from 'react';
import axios from '../services/AxiosConfig';
import { toast } from 'react-toastify';
import { exportToCSV } from '../utils/exportToCSV';
import ExpenseModal from '../components/ExpenseModal';
import EditExpenseModal from '../components/EditExpenseModal';
import { useNavigate } from 'react-router-dom';
import IncomeModal from '../components/IncomeModal';
import '../App.css';
import ExpenseChart from '../components/ExpenseChart';

const AdminDashboard = () => {
  const [familyname, setFamilyname] = useState('');
  const [month, setMonth] = useState('2025-11');
  const [incomeAmount, setIncomeAmount] = useState('');
  const [income, setIncome] = useState(null);
  const [expenses, setExpenses] = useState([]);
  const [showExpenseModal, setShowExpenseModal] = useState(false);
  const [showIncomeModal, setShowIncomeModal] = useState(false);
  const [showEditExpenseModal, setShowEditExpenseModal] = useState(false);
  const [selectedExpense, setSelectedExpense] = useState(null);
  const navigate = useNavigate();
 
  
  const handleSignOut = () => {
    localStorage.clear();
    navigate('/');
  };

  const handleAddExpense = async (form) => {
    try {
      await axios.post('/api/expenses', form);
      toast.success('Expense added');
      setShowExpenseModal(false);
      fetchExpenses();
    } catch {
      toast.error('Failed to add expense');
    }
  };

  const handleEditExpense = async (form) => {
    try {
      if (!selectedExpense || !selectedExpense.id) {
        toast.error('No expense selected');
        return;
      }

      await axios.patch(`/api/expenses/${selectedExpense.id}`, form);
      toast.success('Expense updated');
      setShowEditExpenseModal(false);
      setSelectedExpense(null);
      fetchExpenses();
    } catch {
      toast.error('Failed to update expense');
    }
  };

  const handleOpenEditModal = (expense) => {
    setSelectedExpense(expense);
    setShowEditExpenseModal(true);
  };

  const handleEditIncome = async (form) => {
    try {
      if (!familyname) {
        toast.error('Family name is required');
        return;
      }
      await axios.post('/api/income', { familyname, month, amount: Number(form.amount) });
      setIncomeAmount(form.amount);
      toast.success('Income saved');
      setShowIncomeModal(false);
      fetchIncome();
    } catch {
      console.error('Failed to save income');
      toast.error('Failed to save income');
    }
  };

  const fetchIncome = async () => {
    try {
      const res = await axios.get(`/api/income/all?familyname=${familyname}`);
      const match = Array.isArray(res.data) ? res.data.find(i => i && i.month === month) : null;
      setIncome(match || null);
      if (match && match.familyname) setFamilyname(match.familyname);
    } catch {
      console.error('Failed to fetch income');
      toast.error('Failed to fetch income');
    }
  };

  const fetchExpenses = async () => {
    try {
      const res = await axios.get(`/api/expenses/all?familyname=${familyname}`);
      setExpenses(Array.isArray(res.data) ? res.data.filter(e => e && e.date && e.date.startsWith(month)) : []);
    } catch {
      console.error('Failed to fetch expenses');
      toast.error('Failed to fetch expenses');
    }
  };

  const handleIncomeSubmit = async e => {
    e.preventDefault();
    // await axios.get(`/api/income?month=${month}`);}
    try {
      if (!familyname) {
        toast.error('Family name is required');
        return;
      }
      await axios.post('/api/income', { familyname, month, amount: Number(incomeAmount) });
      toast.success('Income saved');
      fetchIncome();
    } catch {
      console.error('Failed to save income');
      toast.error('Failed to save income');
    }
  };

//   const handleIncomeSubmit = async e => {
//   e.preventDefault();
//   try {
//     await axios.post('/api/income', {
//       month,
//       amount: incomeAmount
//     });
//     toast.success('Income saved');
//     fetchIncome();
//   } catch {
//     toast.error('Failed to save income');
//   }
// };


  const handleDeleteExpense = async id => {
    try {
      await axios.delete(`/api/expenses/${id}`);
      toast.success('Expense deleted');
      fetchExpenses();
    } catch {
      toast.error('Failed to delete expense');
    }
  };

  const handleExportIncome = () => {
    if (income) exportToCSV([income], `${familyname}_${month}_income.csv`);
  };

  const handleExportExpenses = () => {
    exportToCSV(expenses, `${familyname}_${month}_expenses.csv`);
  };

  useEffect(() => {
    fetchIncome();
    fetchExpenses();
  }, [familyname, month]);

  return (
    <div className="admin-page">
      <div className="d-flex justify-content-between p-3 bg-dark fixed-header">
        <h2 className="mb-3 text-light">{income && <strong> {income.familyname}'s family </strong>}</h2>
        <button className="btn btn-outline-light" onClick={handleSignOut}>Sign Out</button>
      </div>
      <div className="container mt-4">
      
      <div className="row mb-4">
        <div className="col-md-4">
          {/* <h3>{income && <strong> {income.familyname}'s family </strong> }</h3> */}
          {/* <input className="form-control" value={familyname} onChange={e => setFamilyname(e.target.value)} /> */}
        </div>
      </div>

     <div className="row mb-4">
        <div className="col-md-4">
          <label>Month</label>
          <input type="month" className="form-control" value={month} onChange={e => setMonth(e.target.value)} />
        </div>
    </div>
    
      {/* <form className="mb-4"> */}
        <h5>Income for ({month})</h5>
        {income && <p><strong>Current Income:</strong> <h3>₹{income.amount}</h3></p>}
        <div className="mb-3">
          {/* <input
            type="number"
            className="form-control"
            placeholder="Income Amount"
            value={incomeAmount}
            onChange={e => setIncomeAmount(e.target.value)}
            required
          /> */}
          <button className="btn btn-success me-2" onClick={()=> setShowIncomeModal(true)}>&nbsp;Edit Income&nbsp;</button>
          <button className="btn btn-outline-primary" onClick={handleExportIncome}>Export Income CSV&nbsp;</button>
        </div>
        <IncomeModal show={showIncomeModal} onHide={() => setShowIncomeModal(false)} onSubmit={handleEditIncome} existingIncome={income} />
      {/* </form> */}

      

      <div className="mb-3">
         <button className="btn btn-primary me-2" onClick={() => setShowExpenseModal(true)}>
          Add Expense
        </button>
        <button className="btn btn-outline-secondary" onClick={handleExportExpenses}>Export Expense CSV</button>
       
      </div>
        <br/>
      {/* <div className="mb-3">
        
      </div> */}
      <ExpenseModal show={showExpenseModal} onHide={() => setShowExpenseModal(false)} onSubmit={handleAddExpense} />
      <EditExpenseModal show={showEditExpenseModal} onHide={() => setShowEditExpenseModal(false)} onSubmit={handleEditExpense} existingExpense={selectedExpense} />
      <h5>Expenses</h5>
      <ul className="list-group">
        {expenses.map(exp => (
          <li key={exp.id} className="list-group-item d-flex justify-content-between align-items-center">
            <div>
              <strong>{exp.category}</strong> - ₹{exp.amountSpent} on {exp.date}
              {exp.note && <div className="text-muted">Note: {exp.note}</div>}
            </div>
            <div>
              <button className="btn btn-sm btn-warning me-2" onClick={() => handleOpenEditModal(exp)}>Update</button>
              <button className="btn btn-sm btn-danger" onClick={() => handleDeleteExpense(exp.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
      <br/><br/>
      <div>
        <h3>remaining budget: ₹{income ? income.amount - expenses.reduce((total, e) => total + (e.amountSpent || 0), 0) : 0}</h3>
      </div><br/>
      <ExpenseChart expenses={expenses} />
    </div>
    </div>
  );
};

export default AdminDashboard;
