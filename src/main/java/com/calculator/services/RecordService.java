package com.calculator.services;

import com.calculator.entity.Operation;

import java.math.BigDecimal;
import java.util.Stack;

public interface RecordService {
    void execute(Stack<Operation> recordStack, Stack<BigDecimal> valueStack, String value, int position);
}
