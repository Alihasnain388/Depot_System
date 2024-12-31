package model;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private HashMap<String, Parcel> parcels; // Collection of Parcels

    public ParcelMap() {
        parcels = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getParcelID(), parcel);
    }

    public Parcel getParcel(String parcelID) {
        return parcels.get(parcelID);
    }

    public void removeParcel(String parcelID) {
        parcels.remove(parcelID);
    }

    
    public Map<String, Parcel> getParcels() {
        return parcels;
    }
}

