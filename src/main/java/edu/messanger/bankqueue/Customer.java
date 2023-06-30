package edu.messanger.bankqueue;

public class Customer implements Runnable {

    private int customerId;
    private int serviceTime;

    public Customer(int customerId, int serviceTime) {
        this.customerId = customerId;
        this.serviceTime = serviceTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("Customer " + customerId + " started.");
        // Perform any necessary operations or tasks here.
        System.out.println("Customer " + customerId + " completed.");
    }
}
