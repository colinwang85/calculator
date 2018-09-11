package com.calculator.record;

import com.calculator.operator.OperatorService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Operation {
    private Integer position;
    private OperatorService op;
    private BigDecimal[] operands;

    public Operation(Integer position, OperatorService op, BigDecimal[] operands) {
        this.position = position;
        this.op = op;
        this.operands = operands;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public OperatorService getOp() {
        return op;
    }

    public void setOp(OperatorService op) {
        this.op = op;
    }

    public BigDecimal[] getOperands() {
        return operands;
    }

    public void setOperands(BigDecimal[] operands) {
        this.operands = operands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(position, operation.position) &&
                Objects.equals(op, operation.op) &&
                Arrays.equals(operands, operation.operands);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(position, op);
        result = 47 * result + Arrays.hashCode(operands);
        return result;
    }
}
