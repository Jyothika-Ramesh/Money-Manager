import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const EditExpenseModal = ({ show, onHide, onSubmit, existingExpense }) => {
  const [form, setForm] = useState({
    category: '',
    amountSpent: '',
    date: '',
    note: ''
  });

  useEffect(() => {
    if (existingExpense) {
      setForm({
        category: existingExpense.category || '',
        amountSpent: existingExpense.amountSpent || '',
        date: existingExpense.date || '',
        note: existingExpense.note || ''
      });
    } else {
      setForm({ category: '', amountSpent: '', date: '', note: '' });
    }
  }, [existingExpense, show]);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Edit Expense</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Label>Category</Form.Label>
            <Form.Control 
              name="category" 
              placeholder="Category" 
              value={form.category}
              onChange={handleChange} 
              required 
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Amount</Form.Label>
            <Form.Control 
              name="amountSpent" 
              type="number" 
              placeholder="Amount" 
              value={form.amountSpent}
              onChange={handleChange} 
              required 
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Date</Form.Label>
            <Form.Control 
              name="date" 
              type="date" 
              value={form.date}
              onChange={handleChange} 
              required 
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Note</Form.Label>
            <Form.Control 
              as="textarea" 
              name="note" 
              placeholder="Note" 
              value={form.note}
              onChange={handleChange} 
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onHide}>Cancel</Button>
          <Button type="submit" variant="primary">Update</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default EditExpenseModal;
