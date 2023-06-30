package edu.messanger.bankqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BankQueue {
    private final BlockingQueue<Customer> queue;

    public BankQueue() {
        queue = new ArrayBlockingQueue<>(10);
    }

    public void enqueue(Customer customer) throws InterruptedException {
        queue.put(customer);
        System.out.println("Customer " + customer.getCustomerId() + " joined the queue.");
    }

    public Customer dequeue() throws InterruptedException {
        Customer customer = queue.take();
        System.out.println("Customer " + customer.getCustomerId() + " left the queue.");
        return customer;
    }
}
