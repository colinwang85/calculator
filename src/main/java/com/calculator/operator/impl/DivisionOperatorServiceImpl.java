package com.calculator.operator.impl;

import com.calculator.record.Operation;
import com.calculator.operator.OperatorService;
import com.calculator.utils.NumberFormatUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
public class DivisionOperatorServiceImpl implements OperatorService {
    @Override
    public String getOperatorSignal() {
        return "/";
    }

    @Override
    public Integer getParameterNumbers() {
        return 2;
    }

    @Override
    public Operation operator(Stack<BigDecimal> paraStack) {
        BigDecimal a = paraStack.pop();
        BigDecimal b = paraStack.pop();
        paraStack.push(b.divide(a, NumberFormatUtil.SCALE_STORE, NumberFormatUtil.ROUNDING_MODE));
        return new Operation(paraStack.size(), this, new BigDecimal[]{b, a});
    }

}

