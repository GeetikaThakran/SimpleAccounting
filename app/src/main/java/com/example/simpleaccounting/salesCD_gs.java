package com.example.simpleaccounting;

public class salesCD_gs {

    private long id;
    private String billNum;
    private String date;
    private String clientName;
    private String notes;
    private String amount;

    public salesCD_gs(long id,String billNum, String date, String clientName, String notes, String amount) {
        this.id = id;
        this.billNum = billNum;
        this.date = date;
        this.clientName = clientName;
        this.notes = notes;
        this.amount = amount;
    }

    public long getId(){
        return id;
    }
    public String getBillNum() {
        return billNum;
    }

    public String getDate() {
        return date;
    }

    public String getClientName() {
        return clientName;
    }

    public String getNotes() {
        return notes;
    }

    public String getAmount() {
        return amount;
    }
}
