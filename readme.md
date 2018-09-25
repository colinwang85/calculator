1. program basic model:
in main.java.com.calculator
config:
    config file
control:
    calculator control
exception:
    self-defined exception
services:
    all operator services, include + - * / sqrt clear undo
entity
    operation entity
utils
    bigDecimal format
validator
    validate the input value
CalculatorApplication is the program entry

in test.java.com.calculator
CalculatorApplicationTests: basis operation test
CalculatorFunctionalTest: function test

in test.testDB
<testname>.txt: test input 
<testname>.expect: expect result 
<testname>.out: after executing CalculatorFunctionalTest, these file will be generated to compare with <testname>.expect

2. environment
using springboot2.0.4 + maven3.5.4 + java 1.8.0

3. local development env setup
clone all source code from github and import the program calculator

