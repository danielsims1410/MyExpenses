package com.example.danie.coursework1;
import java.io.Serializable;

public class ExpenseTemplate implements Serializable
{

    private String description;
    private String cost;
    private String receiptDate;
    private String dateAddedtoApp;
    private int vatint;
    private String totalCost;
    private int expensepaid;
    private int id;
    private String datePaid;
    private byte[] image;

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getDesc() {
        return description;
    }
    public void setDesc(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getReceiptDate() {
        return receiptDate;
    }
    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getDateAddedtoApp() {
        return dateAddedtoApp;
    }
    public void setDateAddedtoApp(String dateAddedtoApp) {
        this.dateAddedtoApp = dateAddedtoApp;
    }

    public int getVAT() {
        return vatint;
    }
    public void setVAT(int vat) { this.vatint = vat; }

    public int getExpensePaid() { return expensepaid; }
    public void setExpensepaid(int expensepaid) { this.expensepaid = expensepaid; }

    public String getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getDatePaid() { return datePaid; }
    public void setDatePaid(String datePaid) { this.datePaid = datePaid; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

}
