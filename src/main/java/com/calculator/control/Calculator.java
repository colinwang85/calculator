package com.calculator.control;

import com.calculator.config.OperatorConfig;
import com.calculator.exception.InsufficientParametersException;
import com.calculator.record.RecordManager;
import com.calculator.record.Operation;
import com.calculator.operator.*;
import com.calculator.operator.impl.*;
import com.calculator.utils.NumberFormatUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator implements Runnable {

    private Stack<BigDecimal> paramStack = new Stack<>();

    private RecordManager recordManager = new RecordManager();

    private Map<String, OperatorService> operatorsMap = new HashMap<>();

    public Calculator() {
        loadServices();
        this.run();
    }

    private void loadServices() {
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

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = br.readLine();
            while (!input.equals("q")) {
                if (!StringUtils.isEmpty(input)) {
                    execute(input);
                    display();
                }
                input = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void execute(String input) {
        int position = 0;
        try {
            for (String splitInput : input.split(" ")) {
                position++;
                if (splitInput == null) {
                    continue;
                }
                int splitInputLength = splitInput.length();
                splitInput = splitInput.trim().toLowerCase();
                if (StringUtils.isEmpty(splitInput)) {
                    position += splitInputLength;
                    continue;
                }

                if (splitInput.equals("undo")) {
                    recordManager.undo(paramStack);
                } else if (splitInput.matches("^[0-9]*$")) {
                    recordManager.record(loadOperation(splitInput));
                } else {
                    OperatorService op = operatorsMap.get(splitInput);
                    if (op == null) {
                        throw new UnsupportedOperationException(op + " unsupported operator");
                    }
                    if (op.getParameterNumbers() > paramStack.size()) {
                        throw new InsufficientParametersException(op, position);
                    }
                    recordManager.record(op.operator(paramStack));
                }
                position += splitInputLength;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Operation loadOperation(String op) {
        BigDecimal opValue = paramStack.push(NumberFormatUtil.bigDecimalSaveFormatter(op));
        return new Operation(paramStack.size(), null, new BigDecimal[]{opValue});
    }

    private void display() {
        StringBuilder sb = new StringBuilder();
        sb.append("stack: ");
        for (BigDecimal value : paramStack) {
            sb.append(NumberFormatUtil.bigDecimalReadFormatter(value) + " ");
        }
        System.out.println(sb.toString());
    }

}

