package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.exception.InsufficientParametersException;
import com.calculator.services.OperatorService;
import com.calculator.services.RecordService;
import com.calculator.validator.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
@InputHandler(categoryOp="[\\+\\-\\*/]|^sqrt$|^clear$")
public class OperatorRecordServiceImpl implements RecordService {
    private static Map<String, OperatorService> operatorsMap = new HashMap<>();
    @Autowired
    private AddOperatorServiceImpl add;
    @Autowired
    private ClearOperatorServiceImpl clear;
    @Autowired
    private SqrtOperatorServiceImpl sqrt;
    @Autowired
    private MinusOperatorServiceImpl minus;
    @Autowired
    private MultiplyOperatorServiceImpl multiply;
    @Autowired
    private DivisionOperatorServiceImpl division;

    private void loadOperators(){
        operatorsMap.putIfAbsent(add.getOperatorSignal(), add);
        operatorsMap.putIfAbsent(clear.getOperatorSignal(), clear);
        operatorsMap.putIfAbsent(sqrt.getOperatorSignal(), sqrt);
        operatorsMap.putIfAbsent(minus.getOperatorSignal(), minus);
        operatorsMap.putIfAbsent(multiply.getOperatorSignal(), multiply);
        operatorsMap.putIfAbsent(division.getOperatorSignal(), division);
    }

    @Override
    public void execute(Stack<Operation> recordStack, Stack<BigDecimal> valueStack, String value, int position) {
        loadOperators();
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
