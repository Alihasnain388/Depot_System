package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dataloader {
    public static void loadParcels(String filename, ParcelMap parcelMap) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
            	// extraction of content from file
                String[] parts = line.split(","); 
                String parcelID = parts[0];
                int daysInDepot = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                Dimension dimension = new Dimension(
                        Integer.parseInt(parts[3]), 
                        Integer.parseInt(parts[4]),
                        Integer.parseInt(parts[5])
                );
                parcelMap.addParcel(new Parcel(parcelID, daysInDepot, weight, dimension));
            }
        }
    }

    public static void loadCustomers(String filename, QueueCus queueCus) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                queueCus.enqueue(new Customer(parts[0], parts[1]));
            }
        }
    }
}

