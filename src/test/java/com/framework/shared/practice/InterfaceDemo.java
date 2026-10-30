package com.framework.shared.practice;

//It is used to achieve abstraction and supports multiple inheritance of type.
interface Payment {
    void pay(int amount);
}

interface TransactionLogger {
    void logTransaction(String transactionId, int amount);
}

// UPI payment implementation
// A class can implement more than one interface
class UPIPayment implements Payment, TransactionLogger {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using UPI");
    }

    public void logTransaction(String transactionId, int amount) {
        System.out.println("Logging transaction: " + transactionId + " with amount: " + amount);
    }
}

// Card payment implementation
class CardPayment implements Payment {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Card");
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {

        Payment p1 = new UPIPayment();
        p1.pay(500);
        TransactionLogger p2 = new UPIPayment();
        p2.logTransaction("TXN001", 500);

        Payment p3 = new CardPayment();
        p3.pay(1000);
    }
}
