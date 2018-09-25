package com.calculator.services;

import com.calculator.entity.Operation;

import java.math.BigDecimal;
import java.util.Stack;

public interface OperatorService {
    String getOperatorSignal();
    Integer getParameterNumbers();
    Operation operator(Stack<BigDecimal> paraStack );
}
