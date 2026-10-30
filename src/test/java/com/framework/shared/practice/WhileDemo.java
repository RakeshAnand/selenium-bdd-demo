package com.framework.shared.practice;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhileDemo {
    private static final Logger logger = LogManager.getLogger(WhileDemo.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("--- MENU ---");
            System.out.println("1. Start Game");
            System.out.println("2. Options");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            choice = scanner.nextInt(); // Take input from user

        } while (choice < 1 || choice > 3); // Repeat if input is invalid!

        logger.info("Selected menu option: {}", choice);
        System.out.println("You selected a valid option: " + choice);
        scanner.close();
    }
}
