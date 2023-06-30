package edu.messanger.bankqueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankQueue bankQueue = new BankQueue();
        Thread[] windowThreads = new Thread[3]; // Create 3 bank windows

        // Create and enqueue customers
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(i, (int) (Math.random() * 2000) + 1000);
            bankQueue.enqueue(customer);
            Thread.sleep(500); // Add a small delay between customer arrivals
        }

        // Start bank window threads
        for (int i = 0; i < windowThreads.length; i++) {
            BankWindow bankWindow = new BankWindow(i + 1, bankQueue);
            windowThreads[i] = new Thread(bankWindow);
            windowThreads[i].start();
        }


        // Wait for all customers to be served
        for (Thread windowThread : windowThreads) {
            windowThread.join();
        }

        System.out.println("All customers have been served.");
    }
}
