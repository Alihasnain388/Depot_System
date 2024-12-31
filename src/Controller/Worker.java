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
    
    public double calculateFee(Parcel parcel) {
        int baseFee = (parcel.getWeight() * 2) + (parcel.getDaysInDepot() * 2); // Each weight and days add 2 to the fees 
        double volumeFee = parcel.getVolume() * 0.1; // Each unit of volume adds 0.1 to the fees
        double totalFee = baseFee + volumeFee;

        if (parcel.getParcelID().startsWith("C")) {
            totalFee *= 0.8; // Apply 20% discount for parcels with 'C' prefix
        }
        return Math.round(totalFee); // Round off means if 20.6 is fees it would be 21.0 
    }

    
}
