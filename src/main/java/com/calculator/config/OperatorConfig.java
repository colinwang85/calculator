package com.calculator.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.calculator.operator.impl")
public class OperatorConfig {
}
