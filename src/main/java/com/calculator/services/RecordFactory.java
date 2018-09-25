package com.calculator.services;

import com.calculator.control.Calculator;
import com.calculator.services.impl.NumberRecordServiceImpl;
import com.calculator.services.impl.OperatorRecordServiceImpl;
import com.calculator.services.impl.UndoRecordServiceImpl;
import com.calculator.validator.InputHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class RecordFactory {
    private static final RecordFactory instance = new RecordFactory();
    private List<RecordService> recordPatternList = new ArrayList<>();
    private static AnnotationConfigApplicationContext context;

    private RecordFactory() {
        context = Calculator.getContext();
        NumberRecordServiceImpl simpleRecord = context.getBean(NumberRecordServiceImpl.class);
        UndoRecordServiceImpl undoRecord = context.getBean(UndoRecordServiceImpl.class);
        OperatorRecordServiceImpl operateRecord = context.getBean(OperatorRecordServiceImpl.class);
        recordPatternList.add(simpleRecord);
        recordPatternList.add(undoRecord);
        recordPatternList.add(operateRecord);
    }

    public RecordService getRecord(String value) {
        for (RecordService service : recordPatternList) {
            InputHandler handler = handleInputValidator(service.getClass());
            if (handler != null && value.matches(handler.categoryOp())) {
                return service;
            }
        }
        OperatorRecordServiceImpl defaultRecordService = context.getBean(OperatorRecordServiceImpl.class);
        return defaultRecordService;
    }

    private InputHandler handleInputValidator(Class<? extends RecordService> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof InputHandler) {
                return (InputHandler) annotation;
            }
        }
        return null;
    }

    public static RecordFactory getInstance() {
        return instance;
    }
}
