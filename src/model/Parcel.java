package model;

public class Parcel {
    private String parcelID;
    private int daysInDepot;
    private int weight;  
    private Dimension dimension;  // Dimension object
    private boolean isCollected; // Status of parcel

    public Parcel(String parcelID, int daysInDepot, int weight, Dimension dimension) {
        this.parcelID = parcelID;
        this.daysInDepot = daysInDepot;
        this.weight = weight;  
        this.dimension = dimension; 
        this.isCollected = false;
    }

    public String getParcelID() {
        return parcelID;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public int getWeight() {
        return weight;  
    }

    public Dimension getDimension() { 
        return dimension;
    }

    public void markAsCollected() {
        this.isCollected = true;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public int getVolume() {
        return dimension.getVolume();
    }

    @Override
    public String toString() {
        return "Parcel ID: " + parcelID + ", Days in Depot: " + daysInDepot +
                ", Weight: " + weight + ", Dimensions: " + dimension.toString();
    }
}
