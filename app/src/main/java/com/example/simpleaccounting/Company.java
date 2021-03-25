package com.example.simpleaccounting;

public class Company {

    private long id;
    private String companyName;
    private String ownerName;
   // private String gstin;
    private String address;
    private String phoneNumber;

    public Company(long id, String companyName, String ownerName, String address, String phoneNumber) {
        this.id = id;
        this.companyName = companyName;
        this.ownerName = ownerName;
       // this.gstin = gstin;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

   // public String getGstin() {
   //     return gstin;
  //  }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
