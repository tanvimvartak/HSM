package com.example.hsm;

public class Query {
    public Query() {}

    public Query(String uid, String name, String phone, String flat, String complaint, String timestamp, String status) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.flat = flat;
        this.complaint = complaint;
        this.timestamp = timestamp;
        this.status =  status;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    private String uid;
    private String name;
    private String phone;
    private String flat;
    private String complaint;
    private String timestamp;




}
