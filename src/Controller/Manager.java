package Controller;

import model.Parcel;
import model.ParcelMap;
import model.QueueCus;
import model.Log;
import model.Dataloader;
import model.Customer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager {
    private QueueCus queueCus; 
    private ParcelMap parcelMap; 
    private Log log;           
    private Worker worker;      // Worker to process customers
    private List<Parcel> collectedParcels; // List of collected parcels

    public Manager() {
        queueCus = new QueueCus();
        parcelMap = new ParcelMap();
        log = Log.getInstance(); 
        worker = new Worker();
        collectedParcels = new ArrayList<>();

      
        try {
            Dataloader.loadParcels("files/Parcels.csv", parcelMap); // Load parcel data
            Dataloader.loadCustomers("files/Custs.csv", queueCus); // Load customer data
        } catch (Exception e) {
            e.printStackTrace();
            log.log("Error loading data: " + e.getMessage());
        }
    }

    public QueueCus getQueueCus() {
        return queueCus;
    }

    public ParcelMap getParcelMap() {
        return parcelMap;
    }

    public Log getLog() {
        return log;
    }

    public List<Parcel> getCollectedParcels() {
        return collectedParcels;
    }

    public void processNextCustomer() {
        Customer customer = queueCus.dequeue(); // Dequeue  next customer
        if (customer != null) {
            String logMessage = worker.processCustomer(customer, parcelMap); 
            markParcelAsCollected(parcelMap.getParcel(customer.getParcelID())); // Mark parcel as collected
            log.log(logMessage); 
            log.log("Customer removed from queue: " + customer.getName() );
        } else {
            log.log("No customers in the queue.");
        }
    }


    public Parcel getCurrentParcel() {
        return worker.getCurrentParcel();
    }

    public void addCustomer(String name, String parcelID) {
        queueCus.enqueue(new model.Customer(name, parcelID));
        log.log("New customer added: " + name + " with Parcel ID: " + parcelID);
    }

    public boolean addParcel(String parcelID, int daysInDepot, int weight, int length, int width, int height) {
        if (queueCus.getQueue().stream().noneMatch(c -> c.getParcelID().equals(parcelID))) {
            log.log("Failed to add parcel: No customer found with Parcel ID " + parcelID);
            return false; // Validation failed
        }
        model.Dimension dimension = new model.Dimension(length, width, height);
        parcelMap.addParcel(new Parcel(parcelID, daysInDepot, weight, dimension));
        log.log("New parcel added: Parcel ID: " + parcelID);
        return true; // Successfully added
    }

    private void markParcelAsCollected(Parcel parcel) {
        parcel.markAsCollected();
        collectedParcels.add(parcel);
    }
}

    



   