package com.calculator.exception;

import com.calculator.operator.OperatorService;

public class InsufficientParametersException extends RuntimeException {
    public InsufficientParametersException(OperatorService op, int position) {
        super("operator " + op.getOperatorSignal() + " (position: " + position + "): insufficient parameters");
    }
}
