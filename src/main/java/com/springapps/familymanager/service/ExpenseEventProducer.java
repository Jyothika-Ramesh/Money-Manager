package com.springapps.familymanager.service;

import com.springapps.familymanager.dto.ExpenseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExpenseEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseEventProducer.class);

    private final KafkaTemplate<String, ExpenseDTO> kafkaTemplate;

    @Value("${family.expense.topic:expenses}")
    private String expenseTopic;

    public ExpenseEventProducer(KafkaTemplate<String, ExpenseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishExpenseEvent(ExpenseDTO expense) {
        try {
            kafkaTemplate.send(expenseTopic, expense.getId(), expense);
            logger.debug("Published expense event to topic {} for id={}", expenseTopic, expense.getId());
        } catch (Exception e) {
            logger.error("Failed to publish expense event for id={}: {}", expense.getId(), e.getMessage(), e);
        }
    }
}
