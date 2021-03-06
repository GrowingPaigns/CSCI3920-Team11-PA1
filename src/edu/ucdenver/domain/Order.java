package edu.ucdenver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


public class Order implements Serializable {
    enum eStatus {IN_PROCESS, FINALIZED}

    //attributes
    private int orderNumber; //FIXME should orderNumber be an int instead of a string?
    private eStatus status;
    private LocalDate dateFinalized;
    private final ArrayList<Product> products;
    private User user;
    
    //FIXME should orderNumber be automatically set in the constructor?
    private static int sequencer = 0;

    //constructors
    public Order(User user)
    {
        sequencer += 1; //order numbers assigned sequentially
        this.orderNumber = sequencer;
        this.status = eStatus.IN_PROCESS;
        this.dateFinalized = null;
        this.products = new ArrayList<Product>();
        this.user = user;
    }

    public Order(User user, ArrayList<Product> products)
    {
        sequencer += 1; //order numbers assigned sequentially
        this.orderNumber = sequencer;
        this.status = eStatus.IN_PROCESS;
        this.dateFinalized = null;
        this.products = products;
        this.user = user;
    }

    //getters and setters
    public int getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public eStatus getStatus() {
        return status;
    }
    public void setStatus(eStatus status) {
        this.status = status;
    }

    public LocalDate getDateFinalized() {
        return dateFinalized;
    }
    public void setDateFinalized(LocalDate dateFinalized) {
        this.dateFinalized = dateFinalized;
    }

    public User getUser (){return this.user;}

    //product methods
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        while(products.remove(product));
    }

    //finalizeOrder and cancel ("finalize" is already a method defined in Object.java)

    public void finalizeOrder() {
        setDateFinalized(LocalDate.now());
        status = eStatus.FINALIZED;
    }
    public void cancel()
    {
        if(status == eStatus.IN_PROCESS)
        {
            //TODO cancel()
            //order will be removed from "orders" in System

        }
    }

    @Override
    public String toString()
    {
        return String.format("Order Number: %d, Status: %s", orderNumber, status.toString());
    }

}
