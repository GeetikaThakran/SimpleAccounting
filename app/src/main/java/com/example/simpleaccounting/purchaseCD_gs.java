package com.example.simpleaccounting;

public class purchaseCD_gs {
    private long id;
    private String billNum;
    private String date;
    private String supplierName;
    private String notes;
    private String amount;

    public purchaseCD_gs(long id, String billNum, String date, String supplierName, String notes, String amount) {
        this.id = id;
        this.billNum = billNum;
        this.date = date;
        this.supplierName = supplierName;
        this.notes = notes;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getBillNum() {
        return billNum;
    }

    public String getDate() {
        return date;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getNotes() {
        return notes;
    }

    public String getAmount() {
        return amount;
    }
}
