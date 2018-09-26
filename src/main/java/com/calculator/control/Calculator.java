package com.calculator.control;

import com.calculator.config.OperatorConfig;
import com.calculator.entity.Operation;
import com.calculator.services.*;
import com.calculator.utils.NumberFormatUtil;
import com.calculator.services.RecordService;
import com.calculator.services.RecordFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    private Stack<BigDecimal> valueStack = new Stack<>();
    private Stack<Operation> operationStack = new Stack<>();

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperatorConfig.class);

    public Calculator() {
        this.run();
    }

    //test only
    public Calculator(String string) {
    }

    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
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
                RecordService record = RecordFactory.getInstance().getRecord(splitInput);
                record.execute(operationStack, valueStack, splitInput, position);
                position += splitInputLength;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void display() {
        StringBuilder sb = new StringBuilder();
        sb.append("stack: ");
        valueStack.forEach(v -> sb.append(NumberFormatUtil.bigDecimalReadScaleFormat(v) + " "));
        System.out.println(sb.toString());
    }

}

