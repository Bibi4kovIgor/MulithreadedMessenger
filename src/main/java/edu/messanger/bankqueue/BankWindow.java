package edu.messanger.bankqueue;

public class BankWindow implements Runnable {

    private final int windowNumber;
    private final BankQueue queue;

    public BankWindow(int windowNumber, BankQueue queue) {
        this.windowNumber = windowNumber;
        this.queue = queue;
    }

    public void serveNextCustomer() throws InterruptedException {
        Customer customer = queue.dequeue();
        System.out.println("Window " + windowNumber + " serving Customer " + customer.getCustomerId());
        Thread.sleep(customer.getServiceTime());
        System.out.println("Window " + windowNumber + " finished serving Customer " + customer.getCustomerId());
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
        try {
            while (true) {
                serveNextCustomer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
