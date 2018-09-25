package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.services.OperatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
public class MinusOperatorServiceImpl implements OperatorService {
    @Override
    public String getOperatorSignal() {
        return "-";
    }

    @Override
    public Integer getParameterNumbers() {
        return 2;
    }

    @Override
    public Operation operator(Stack<BigDecimal> paraStack) {
        BigDecimal a = paraStack.pop();
        BigDecimal b = paraStack.pop();
        paraStack.push(b.subtract(a));
        return new Operation(paraStack.size(), this, new BigDecimal[]{b, a});
    }

}

