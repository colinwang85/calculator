package com.calculator.services.impl;

import com.calculator.config.OperatorConfig;
import com.calculator.entity.Operation;
import com.calculator.exception.InsufficientParametersException;
import com.calculator.services.OperatorService;
import com.calculator.services.RecordService;
import com.calculator.validator.InputHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
@InputHandler(categoryOp="[\\+\\-\\*/]|^sqrt$|^clear$")
public class OperatorRecordServiceImpl implements RecordService {
    private static Map<String, OperatorService> operatorsMap = new HashMap<>();

    static {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperatorConfig.class);
        AddOperatorServiceImpl add = context.getBean(AddOperatorServiceImpl.class);
        operatorsMap.put(add.getOperatorSignal(), add);
        ClearOperatorServiceImpl clear = context.getBean(ClearOperatorServiceImpl.class);
        operatorsMap.put(clear.getOperatorSignal(), clear);
        SqrtOperatorServiceImpl sqrt = context.getBean(SqrtOperatorServiceImpl.class);
        operatorsMap.put(sqrt.getOperatorSignal(), sqrt);
        MinusOperatorServiceImpl minus = context.getBean(MinusOperatorServiceImpl.class);
        operatorsMap.put(minus.getOperatorSignal(), minus);
        MultiplyOperatorServiceImpl mutiply = context.getBean(MultiplyOperatorServiceImpl.class);
        operatorsMap.put(mutiply.getOperatorSignal(), mutiply);
        DivisionOperatorServiceImpl division = context.getBean(DivisionOperatorServiceImpl.class);
        operatorsMap.put(division.getOperatorSignal(), division);
    }

    public void execute(Stack<Operation> recordStack, Stack<BigDecimal> valueStack, String value, int position) {
        OperatorService op = operatorsMap.get(value);
        if (op == null) {
            throw new UnsupportedOperationException(op + " unsupported operator");
        }
        if (op.getParameterNumbers() > valueStack.size()) {
            throw new InsufficientParametersException(op, position);
        }
        recordStack.push(op.operator(valueStack));
    }

}
