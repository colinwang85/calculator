package com.calculator.control;

import com.calculator.config.OperatorConfig;
import com.calculator.exception.InsufficientParametersException;
import com.calculator.entity.Operation;
import com.calculator.services.*;
import com.calculator.services.impl.*;
import com.calculator.utils.NumberFormatUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    private Stack<BigDecimal> paraStack = new Stack<>();

    private RecordManager recordManager = new RecordManager();

    private Map<String, OperatorService> operatorsMap = new HashMap<>();

    public Calculator() {
        loadServices();
        this.run();
    }

    //test only
    public Calculator(String string) {
        loadServices();
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

    public void execute(String input) {
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
                    recordManager.undo(paraStack);
                } else if (splitInput.matches("^[0-9]*$")) {
                    recordManager.record(generateOperation(splitInput));
                } else {
                    OperatorService op = operatorsMap.get(splitInput);
                    if (op == null) {
                        throw new UnsupportedOperationException(op + " unsupported operator");
                    }
                    if (op.getParameterNumbers() > paraStack.size()) {
                        throw new InsufficientParametersException(op, position);
                    }
                    recordManager.record(op.operator(paraStack));
                }
                position += splitInputLength;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Operation generateOperation(String op) {
        BigDecimal opValue = NumberFormatUtil.bigDecimalScaleFormat(op);
        return new Operation(paraStack.size(), null, new BigDecimal[]{paraStack.push(opValue)});
    }

    public void display() {
        StringBuilder sb = new StringBuilder();
        sb.append("stack: ");
        paraStack.forEach(v -> sb.append(NumberFormatUtil.bigDecimalReadScaleFormat(v) + " "));
        System.out.println(sb.toString());
    }

}

