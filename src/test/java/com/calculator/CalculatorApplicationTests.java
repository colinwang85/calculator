package com.calculator;

import com.calculator.operator.OperatorService;
import com.calculator.operator.impl.*;
import com.calculator.record.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Stack;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorApplicationTests {
    private Stack<BigDecimal> input = new Stack<>();

    @Test
    public void testAdd() {
        testOperator(new AddOperatorServiceImpl(), BigDecimal.valueOf(13));
    }

    @Test
    public void testDivision() {
        testOperator(new DivisionOperatorServiceImpl(), BigDecimal.valueOf(2.25));
    }

    @Test
    public void testMinus() {
        testOperator(new MinusOperatorServiceImpl(), BigDecimal.valueOf(5));
    }

    @Test
    public void testSqrt() {
        testOperator(new SqrtOperatorServiceImpl(), BigDecimal.valueOf(2));
    }

    @Test
    public void testMultiply() {
        testOperator(new MultiplyOperatorServiceImpl(), BigDecimal.valueOf(36));
    }

    @Test
    public void testClear() {
        ClearOperatorServiceImpl clearOperatorService = new ClearOperatorServiceImpl();
        Operation o = clearOperatorService.operator(input);
        assertTrue(input.empty());
    }

    @Before
    public void initializeStack() {
        input.push(BigDecimal.valueOf(9));
        input.push(BigDecimal.valueOf(4));
    }

    private void testOperator(OperatorService operatorService, BigDecimal expected) {
        Operation o = operatorService.operator(input);
        BigDecimal result = input.peek();
        assertTrue(0 == result.compareTo(expected));
    }

}
