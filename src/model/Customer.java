package model;

public class Customer {
    private static int sequenceCounter = 0; 
    private int sequenceNo;
    private String name;     
    private String parcelID; 

    public Customer(String name, String parcelID) {
        this.sequenceNo = ++sequenceCounter; 
        this.name = name;
        this.parcelID = parcelID;
    }

    public int getSequenceNo() {
        return sequenceNo; 
    }

    public String getName() {
        return name; 
    }

    public String getParcelID() {
        return parcelID;
    }

    @Override
    public String toString() {
        return "Customer :" + sequenceNo + ": " + name + ", Parcel ID: " + parcelID;
    }
}

