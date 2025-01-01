package View;

import Controller.Manager;

import javax.swing.*;
import java.awt.*;

public class ParcelDepotGUI extends JPanel {
    private Manager manager; // Reference to the manager

    // GUI Components
    private JList<String> customerList;
    private JList<String> parcelList;
    private JList<String> collectedParcelList;
    private JTextArea logArea;
    private JTextArea currentParcelArea;
    private JTextField searchField; 
    private JButton searchButton; 

    public ParcelDepotGUI(Manager manager) {
        this.manager = manager;

        
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel(new BorderLayout());

        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField(15);
        searchButton = new JButton("Search Parcel");
        searchButton.addActionListener(e -> searchParcel());
        searchPanel.add(new JLabel("Enter Parcel ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        
        topPanel.add(searchPanel, BorderLayout.NORTH);

       
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));

        // Parcel Button
        JButton addParcelButton = new JButton("Add Parcel");
        addParcelButton.setPreferredSize(new Dimension(150, 30)); 
        addParcelButton.addActionListener(e -> addParcel());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(addParcelButton);
        buttonPanel.add(leftPanel, BorderLayout.WEST);

        // Process Next Customer Button 
        JButton processCustomerButton = new JButton("Process Next Customer");
        processCustomerButton.setPreferredSize(new Dimension(170, 30)); 
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(processCustomerButton);
        processCustomerButton.addActionListener(e -> processCustomer());
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        //  Customer Button
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setPreferredSize(new Dimension(150, 30)); 
        addCustomerButton.addActionListener(e -> addCustomer());
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(addCustomerButton);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        add(topPanel, BorderLayout.NORTH);

        // Panel for Customer list, UnCollected parcel list, and Collected parcels lists
        JPanel listPanel = new JPanel(new GridLayout(1, 3));

        // Customer List
        customerList = new JList<>();
        JScrollPane customerScrollPane = new JScrollPane(customerList);
        customerScrollPane.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        listPanel.add(customerScrollPane);

        // UnCollected Parcel list
        parcelList = new JList<>();
        JScrollPane parcelScrollPane = new JScrollPane(parcelList);
        parcelScrollPane.setBorder(BorderFactory.createTitledBorder("Uncollected Parcels"));
        listPanel.add(parcelScrollPane);

        // Collected Parcel List
        collectedParcelList = new JList<>();
        JScrollPane collectedParcelScrollPane = new JScrollPane(collectedParcelList);
        collectedParcelScrollPane.setBorder(BorderFactory.createTitledBorder("Collected Parcels"));
        listPanel.add(collectedParcelScrollPane);

        
        add(listPanel, BorderLayout.CENTER);

        // Log Area
        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Logs"));
        add(logScrollPane, BorderLayout.SOUTH);

        // Current Parcel Processed Area
        currentParcelArea = new JTextArea(5, 40);
        currentParcelArea.setEditable(false);
        JScrollPane currentParcelScrollPane = new JScrollPane(currentParcelArea);
        currentParcelScrollPane.setBorder(BorderFactory.createTitledBorder("Current Parcel Being Processed"));
        add(currentParcelScrollPane, BorderLayout.WEST);

     
        updateCustomerList();
        updateParcelList();
        updateLogArea();
        updateCurrentParcel();
    }

    private void searchParcel() {
        String parcelID = searchField.getText().trim(); // Get Input from search bar
        if (parcelID.isEmpty()) {
            String message = "Search failed: No Parcel ID entered.";
            manager.getLog().log(message); 
            updateLogArea(); 
            return;
        }

        // Check if the parcel exits
        model.Parcel parcel = manager.getParcelMap().getParcel(parcelID);
        if (parcel == null) {
            String message = "Search result: Parcel ID " + parcelID + " is Not in the list.";
            manager.getLog().log(message); 
        } else {
            String status = parcel.isCollected() ? "Collected" : "Waiting for collection";
            String message = "Search result: Parcel ID " + parcelID + " is " + status + ".";
            manager.getLog().log(message); 
        }

        updateLogArea(); 
    }


    private void addCustomer() {
        String name = JOptionPane.showInputDialog(this, "Enter Customer Name:");
        String parcelID = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
        if (name != null && parcelID != null) {
            manager.addCustomer(name, parcelID);
            updateCustomerList();
            updateLogArea();
        }
    }

    private void addParcel() {
        try {
            String parcelID = JOptionPane.showInputDialog(this, "Enter Parcel ID:");
            if (parcelID == null || !manager.addParcel(parcelID,
                    Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Days in Depot:")),
                    Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Weight:")),
                    Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Length:")),
                    Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Width:")),
                    Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Height:")))) {
                JOptionPane.showMessageDialog(this, "Parcel can't be added. Parcel ID should be is linked to an existing customer.");
                return;
            }
            updateParcelList();
            updateLogArea();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void processCustomer() {
        manager.processNextCustomer();
        updateCustomerList();
        updateParcelList();
        updateCurrentParcel();
        updateLogArea();
    }

    private void updateCustomerList() {
        String[] customerArray = manager.getQueueCus().getQueue().stream()
                .map(c -> "Customer :" + c.getSequenceNo() + ": " + c.getName() +
                        " (Parcel ID: " + c.getParcelID() + ")")
                .toArray(String[]::new);
        customerList.setListData(customerArray);
    }

    private void updateParcelList() {
        // Update uncollected parcels
        String[] uncollectedArray = manager.sortingBySurname().stream()
                .map(p -> "Parcel ID: " + p.getParcelID() + ", Weight: " + p.getWeight() +
                        ", Dimensions: " + p.getDimension() +
                        ", Days in Depot: " + p.getDaysInDepot())
                .toArray(String[]::new);
        parcelList.setListData(uncollectedArray);

        // Update collected parcels
        String[] collectedArray = manager.getCollectedParcels().stream()
                .map(p -> "Parcel ID: " + p.getParcelID())
                .toArray(String[]::new);
        collectedParcelList.setListData(collectedArray);
    }

    private void updateLogArea() {
        logArea.setText(manager.getLog().getLogBufferContents());
    }

    private void updateCurrentParcel() {
        model.Parcel currentParcel = manager.getCurrentParcel();
        if (currentParcel != null) {
            currentParcelArea.setText("Parcel ID: " + currentParcel.getParcelID() +
                    "\nWeight: " + currentParcel.getWeight() +
                    "\nDimensions: " + currentParcel.getDimension() +
                    "\nDays in Depot: " + currentParcel.getDaysInDepot());
        } else {
            currentParcelArea.setText("No parcel currently being processed.");
        }
    }
}
