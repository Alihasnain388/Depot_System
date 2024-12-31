package model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueCus {
    private Queue<Customer> customers;  // Queue of Customers

    public QueueCus() {
        customers = new LinkedList<>();
    }

    // Add
    public void enqueue(Customer customer) {
        customers.add(customer);
    }

    // Remove
    public Customer dequeue() {
        return customers.poll();
    }
    // Empty or Not
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public Queue<Customer> getQueue() {
        return customers;
    }
}
