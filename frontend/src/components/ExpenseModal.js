import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const ExpenseModal = ({ show, onHide, onSubmit }) => {
  const [form, setForm] = useState({
    category: '',
    amountSpent: '',
    date: '',
    note: ''
  });

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Add Expense</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Control name="category" placeholder="Category" onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Control name="amountSpent" type="number" placeholder="Amount" onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Control name="date" type="date" onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Control as="textarea" name="note" placeholder="Note" onChange={handleChange} />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onHide}>Cancel</Button>
          <Button type="submit" variant="primary">Save</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ExpenseModal;
