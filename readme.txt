idea compile step:
Maven projects->LifeCycle->package->run calculator[package]
the jar path target/calculator-0.0.1-SNAPSHOT.jar as the pom config

testDB include <testname>.txt as test input and <testname>.expect as expect result before run CalculatorFunctionalTest.
after run CalculatorFunctionalTest, <testname>.out will be generated and the assert will compare the <testname>.out and <testname>.expect