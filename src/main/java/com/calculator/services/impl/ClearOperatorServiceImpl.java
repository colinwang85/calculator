package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.services.OperatorService;
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
        Stack<BigDecimal> all = new Stack();
        all.addAll(paraStack);
        paraStack.clear();
        return new Operation(0, this, all.toArray(new BigDecimal[]{}));
    }
}
