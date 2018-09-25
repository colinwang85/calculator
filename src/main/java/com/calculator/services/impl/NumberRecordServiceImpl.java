package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.services.RecordService;
import com.calculator.utils.NumberFormatUtil;
import com.calculator.validator.InputHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
@InputHandler(categoryOp = "^[0-9]*$")
public class NumberRecordServiceImpl implements RecordService {
    @Override
    public void execute(Stack<Operation> recordStack, Stack<BigDecimal> valueStack, String value, int position) {
        recordStack.push(generateOperation(valueStack, value));
    }

    private Operation generateOperation(Stack<BigDecimal> valueStack, String value) {
        BigDecimal opValue = NumberFormatUtil.bigDecimalScaleFormat(value);
        return new Operation(valueStack.size(), null, new BigDecimal[]{valueStack.push(opValue)});
    }
}
