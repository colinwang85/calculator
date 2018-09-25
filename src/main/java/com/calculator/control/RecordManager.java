package com.calculator.control;

import com.calculator.entity.Operation;

import java.math.BigDecimal;
import java.util.Stack;

public class RecordManager {
    private Stack<Operation> record = new Stack<>();

    public void record(Operation op) {
        record.push(op);
    }

    public Operation getPreviousRecord() {
        if (!record.isEmpty()) {
            return record.pop();
        }
        return null;
    }

    public void undo(Stack<BigDecimal> paramStack) {
        Operation previousOp = getPreviousRecord();
        if (previousOp == null) {
            return;
        }
        if (paramStack.size() > 0) {
            paramStack.pop();
        }
        if (previousOp.getOp() != null) {
            for (BigDecimal p : previousOp.getOperands()) {
                paramStack.push(p);
            }
        }
    }
}
