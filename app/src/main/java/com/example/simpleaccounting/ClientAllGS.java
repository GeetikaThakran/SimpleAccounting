package com.example.simpleaccounting;

public class ClientAllGS {

    private long id;
    private String image;
    private String clientName;
    private String phoneNumber;
    private String firmrName;
    private String email;

// id,name,phone,firm,email,image
    public ClientAllGS(long id,String image, String clientName, String phoneNumber, String firmrName, String email) {
        this.id = id;
        this.image=image;
        this.clientName = clientName;
        this.firmrName = firmrName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }
    public String getImage() { return image; }

    public String getClientName() {
        return clientName;
    }

    public String getFirmrName() {
        return firmrName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
