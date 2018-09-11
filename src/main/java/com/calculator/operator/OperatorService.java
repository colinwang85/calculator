package com.calculator.operator;

import com.calculator.record.Operation;

import java.math.BigDecimal;
import java.util.Stack;

public interface OperatorService {
    String getOperatorSignal();
    Integer getParameterNumbers();
    Operation operator(Stack<BigDecimal> paraStack );
}
