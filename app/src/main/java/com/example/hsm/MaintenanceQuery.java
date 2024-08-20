package com.example.hsm;

import java.io.Serializable;

public class MaintenanceQuery implements Serializable {

    public MaintenanceQuery() {}
    private String m_wing;
    private String m_flat;
    private String m_name;

    public String getMq() {
        return mq;
    }

    public void setMq(String mq) {
        this.mq = mq;
    }

    private String mq;

    public MaintenanceQuery(String m_wing, String m_flat, String m_name,String mq, String m_email, String m_maintenance, String m_sinkingfund, String m_charges, String uid) {
        this.m_wing = m_wing;
        this.m_flat = m_flat;
        this.m_name = m_name;
        this.mq= mq;
        this.m_email = m_email;
        this.m_maintenance = m_maintenance;
        this.m_sinkingfund = m_sinkingfund;
        this.m_charges = m_charges;
        this.uid = uid;

    }

    private String m_email;
    private String m_maintenance;
    private String m_sinkingfund;
    private String m_charges;
    private String uid ;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }









    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_wing() {
        return m_wing;
    }

    public void setM_wing(String m_wing) {
        this.m_wing = m_wing;
    }

    public String getM_flat() {
        return m_flat;
    }

    public void setM_flat(String m_flat) {
        this.m_flat = m_flat;
    }

    public String getM_maintenance() {
        return m_maintenance;
    }

    public void setM_maintenance(String m_maintenance) {
        this.m_maintenance = m_maintenance;
    }

    public String getM_sinkingfund() {
        return m_sinkingfund;
    }

    public void setM_sinkingfund(String m_sinkingfund) {
        this.m_sinkingfund = m_sinkingfund;
    }

    public String getM_charges() {
        return m_charges;
    }

    public void setM_charges(String m_charges) {
        this.m_charges = m_charges;
    }



}
