package com.example.simpleaccounting;

public class SupplierAllGS {

    private long id;
    private String image;
    private String supplierName;
    private String phoneNumber;
    private String firmrName;
    private String email;
//firmrName supplierName phoneNumber image email
    public SupplierAllGS(long id, String email, String image, String phoneNumber, String firmrName,String supplierName) {
        this.id = id;
        this.supplierName = supplierName;
        this.phoneNumber = phoneNumber;
        this.firmrName = firmrName;
        this.email = email;
        this.image=image;
    }

    public long getId() {
        return id;
    }

    public String getImage() { return image; }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirmrName() {
        return firmrName;
    }

    public String getEmail() {
        return email;
    }
}
