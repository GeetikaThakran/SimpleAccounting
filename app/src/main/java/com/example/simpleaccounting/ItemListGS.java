package com.example.simpleaccounting;

public class ItemListGS {
    private long id;
    private String itemName;
    private String hsnCode;
    private String barcode;
    private String price;
    private String gst;

    public ItemListGS(long id, String itemName, String hsnCode, String barcode, String price, String gst) {
        this.id = id;
        this.itemName = itemName;
        this.hsnCode = hsnCode;
        this.barcode = barcode;
        this.price = price;
        this.gst = gst;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}
