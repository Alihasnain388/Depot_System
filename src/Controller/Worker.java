package Controller;

import model.Customer;
import model.Parcel;
import model.ParcelMap;

public class Worker {
    private Parcel currentParcel; // Track current parcel being processed

    public Parcel getCurrentParcel() {
        return currentParcel; // 
    }

    public String processCustomer(Customer customer, ParcelMap parcelMap) { 
        currentParcel = parcelMap.getParcel(customer.getParcelID()); 
        if (currentParcel != null && !currentParcel.isCollected()) {
            currentParcel.markAsCollected();
            double fee = calculateFee(currentParcel);
            // log message 
            String logMessage = "Processed customer: " + customer.getName() +  
                                " | Parcel ID: " + currentParcel.getParcelID()  +
                                " | Fee: $" + fee;
            return logMessage; 
        } else {
            return "Parcel not found or already collected for customer: " + customer.getName();
        }
    }

    
}
