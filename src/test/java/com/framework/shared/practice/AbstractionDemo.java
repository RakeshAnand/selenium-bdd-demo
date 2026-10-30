package com.framework.shared.practice;

abstract class Vehicle {

    // Abstract method
    abstract void start();
}

// Concrete class
class Car extends Vehicle {

    @Override
    void start() {
        System.out.println(
                "Car starts with a key or button.");
    }
}

public class AbstractionDemo {

    public static void main(String[] args) {

        Vehicle vehicle = new Car();
        vehicle.start();
    }
}
