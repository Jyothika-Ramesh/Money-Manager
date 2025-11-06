package com.springapps.familymanager.service;

import com.springapps.familymanager.dto.ExpenseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseEventConsumer.class);

    @Value("${family.expense.topic:expenses}")
    private String expenseTopic;

    @KafkaListener(topics = "${family.expense.topic:expenses}", groupId = "${spring.kafka.consumer.group-id:familymanager-group}")
    public void consumeExpense(ExpenseDTO expense) {
        // For now we just log the received event. In a real app you might react to it.
        logger.info("Consumed expense event from topic {} : id={}, category={}, amount={}", expenseTopic, expense.getId(), expense.getCategory(), expense.getAmount());


    }
}
