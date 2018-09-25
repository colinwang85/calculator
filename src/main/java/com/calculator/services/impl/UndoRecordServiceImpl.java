package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.services.RecordService;
import com.calculator.validator.InputHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
@InputHandler(categoryOp = "^undo$")
public class UndoRecordServiceImpl implements RecordService {
    public void execute(Stack<Operation> recordStack, Stack<BigDecimal> paraStack, String value, int position) {
        if (recordStack.isEmpty()) {
            return;
        }
        Operation previousOp = recordStack.pop();
        if (paraStack.size() > 0) {
            paraStack.pop();
        }
        if (previousOp.getOp() != null) {
            for (BigDecimal p : previousOp.getOperands()) {
                paraStack.push(p);
            }
        }
    }
}
