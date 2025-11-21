import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

const ExpenseChart = ({ expenses }) => {
  const categoryTotals = Array.isArray(expenses) ? expenses.reduce((acc, e) => {
    acc[e.category] = (acc[e.category] || 0) + e.amountSpent;
    return acc;
  }, {}) : {};

  const data = {
    labels: Object.keys(categoryTotals),
    datasets: [{
      label: 'Amount Spent',
      data: Object.values(categoryTotals),
      backgroundColor: 'rgba(75,192,192,0.5)',
      maxBarThickness: 100
    }]
  };

  return (
    <div className="my-4">
      <h3>Category Breakdown</h3><br/>
      <Bar data={data} />
    </div>
  );
};

export default ExpenseChart;
