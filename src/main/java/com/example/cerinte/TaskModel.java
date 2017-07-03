package com.example.cerinte;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cerinte")
public class TaskModel {

    @Id
    private String ID;
    private String persoana="";
    private String echipa="";
    private String tip ="";
    private String descriere ="";
    private String timp ="0";
    private double status =0;
    private String sprint ="0";

    public TaskModel(){

    }
    public TaskModel(String ID , String echipa, String descriere, String timp) {
        this.ID = ID;
        this.persoana = persoana;
        this.echipa = echipa;
        this.descriere = descriere;
        this.timp = timp;
    }

    public TaskModel(String ID, String persoana, String echipa, String tip, String descriere, String timp, double status, String sprint) {
        this.ID = ID;
        this.persoana = persoana;
        this.echipa = echipa;
        this.tip = tip;
        this.descriere = descriere;
        this.timp = timp;
        this.status = status;
        this.sprint = sprint;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
}
