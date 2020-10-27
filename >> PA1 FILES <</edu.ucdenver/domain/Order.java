package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;


public class Order {
    enum eStatus {IN_PROCESS, FINALIZED}

    //variables
    private String orderNumber; //FIXME should orderNumber be an int?
    private eStatus status;
    private LocalDate dateFinalized;
    private final ArrayList<Product> products;

    //constructors
    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
        this.status = eStatus.IN_PROCESS;
        this.dateFinalized = null;
        this.products = new ArrayList<Product>();
    }

    //getters and setters
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
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
    public void cancel() {
        if(status == eStatus.IN_PROCESS) {
            //TODO cancel()
            //order will be removed from "orders" in System

        }
    }

}
