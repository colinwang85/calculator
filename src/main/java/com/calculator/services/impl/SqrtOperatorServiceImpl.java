package com.calculator.services.impl;

import com.calculator.entity.Operation;
import com.calculator.services.OperatorService;
import com.calculator.utils.NumberFormatUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Stack;

@Service
public class SqrtOperatorServiceImpl implements OperatorService {
    @Override
    public String getOperatorSignal() {
        return "sqrt";
    }

    @Override
    public Integer getParameterNumbers() {
        return 1;
    }

    @Override
    public Operation operator(Stack<BigDecimal> paraStack) {
        BigDecimal a = paraStack.pop();
        paraStack.push(NumberFormatUtil.bigDecimalScaleFormat(Math.sqrt(a.doubleValue())));
        return new Operation(paraStack.size(), this, new BigDecimal[]{a});
    }
}
