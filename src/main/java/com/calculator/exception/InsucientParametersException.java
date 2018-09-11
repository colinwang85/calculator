package com.calculator.exception;

import com.calculator.operator.OperatorService;

public class InsucientParametersException extends RuntimeException {
    public InsucientParametersException(OperatorService op, int position) {
        super("operator " + op.getOperatorSignal() + " (position: " + position + "): insucient parameters");
    }
}
