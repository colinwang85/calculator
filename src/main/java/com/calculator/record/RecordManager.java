package com.calculator.record;

import java.math.BigDecimal;
import java.util.Stack;

public class RecordManager {
    private Stack<Operation> record = new Stack<>();

    public void record(Operation op) {
        record.push(op);
    }

    public void undo(Stack<BigDecimal> params) {
        if (!record.isEmpty()) {
            Operation op = record.pop();
            Stack<BigDecimal> tmp = new Stack<>();
            while (params.size() > op.getPosition()) {
                tmp.push(params.pop());
            }
            params.pop();
            if (op.getOp() != null) {
                for (BigDecimal p : op.getOperands()) {
                    params.push(p);
                }
            }
            while (!tmp.isEmpty()) {
                params.push(tmp.pop());
            }
        }
    }

}
