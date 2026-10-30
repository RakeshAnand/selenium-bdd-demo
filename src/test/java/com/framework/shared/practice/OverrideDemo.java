package com.framework.shared.practice;

// Parent class
class Calculator {
    public int my_Sum(int a, int b) {
        System.out.println("Parent class sum method:");
        return a + b;
    }
}

// Child class
class AdvancedCalculator extends Calculator {
    @Override
    public int my_Sum(int a, int b) {
        System.out.println(
                "Child class overridden sum method:");
        return a + b + 10;
    }
}

// Main class
public class OverrideDemo {
    public static void main(String[] args) {
        Calculator calc1 = new Calculator();
        System.out.println(
                "Result: " + calc1.my_Sum(5, 10));
        AdvancedCalculator calc2 = new AdvancedCalculator();
        System.out.println(
                "Result: " + calc2.my_Sum(5, 10));

        // Runtime Polymorphism
        Calculator calc3 = new AdvancedCalculator();
        System.out.println(
                "Result: " + calc3.my_Sum(5, 10));
    }
}