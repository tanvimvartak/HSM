package com.example.hsm;

public class ResidentQuery {
    private String rname;
    private String rwing;
    private String rflat;
    private String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }





    public ResidentQuery() {}



    public ResidentQuery(String rname, String rwing, String rflat, String uid) {
        this.rname = rname;
        this.rwing = rwing;
        this.rflat = rflat;
        this.uid = uid;
    }



    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRwing() {
        return rwing;
    }

    public void setRwing(String rwing) {
        this.rwing = rwing;
    }

    public String getRflat() {
        return rflat;
    }

    public void setRflat(String rflat) {
        this.rflat = rflat;
    }


}
