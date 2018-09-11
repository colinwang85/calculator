package com.calculator.operator.impl;

import com.calculator.record.Operation;
import com.calculator.operator.OperatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
public class ClearOperatorServiceImpl implements OperatorService {
    @Override
    public String getOperatorSignal() {
        return "clear";
    }

    @Override
    public Integer getParameterNumbers() {
        return 0;
    }

    @Override
    public Operation operator(Stack<BigDecimal> paraStack) {
        paraStack.clear();
        return new Operation(0, this, null);
    }
}
