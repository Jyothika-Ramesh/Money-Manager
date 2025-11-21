import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const IncomeModal = ({ show, onHide, onSubmit, existingIncome }) => {
  const [form, setForm] = useState({
    amount: '',
  });

  useEffect(() => {
    if (existingIncome && existingIncome.amount) {
      setForm({ amount: existingIncome.amount });
    } else {
      setForm({ amount: '' });
    }
  }, [existingIncome, show]);

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = e => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>{existingIncome ? 'Edit Income' : 'Add Income'}</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Label>Income Amount</Form.Label>
            <Form.Control 
              name="amount" 
              type="number" 
              placeholder="Income Amount" 
              value={form.amount}
              onChange={handleChange} 
              required 
            />
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

export default IncomeModal;
