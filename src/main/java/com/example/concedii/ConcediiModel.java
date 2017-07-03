package com.example.concedii;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "concedii")
public class ConcediiModel {

    @Id
    private Integer numarConcediu =0;
    private String angajat="";
    private String tipConcediu="";
    private LocalDateTime dataStart;
    private LocalDateTime dataEnd;
    private int nrZile;

    public ConcediiModel(){
    }

    public ConcediiModel(int numarConcediu, LocalDateTime dataStart, LocalDateTime dataEnd, int nrZile) {
        this.numarConcediu = numarConcediu;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.nrZile = nrZile;
    }

    public String getAngajat() {
        return angajat;
    }

    public void setAngajat(String angajat) {
        this.angajat = angajat;
    }

    public void setNumarConcediu(Integer numarConcediu) {
        this.numarConcediu = numarConcediu;
    }

    public String getTipConcediu() {
        return tipConcediu;
    }

    public void setTipConcediu(String tipConcediu) {
        this.tipConcediu = tipConcediu;
    }

    public int getNumarConcediu() {
        return numarConcediu;
    }

    public void setNumarConcediu(int numarConcediu) {
        this.numarConcediu = numarConcediu;
    }

    public LocalDateTime getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDateTime dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDateTime getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(LocalDateTime dataEnd) {
        this.dataEnd = dataEnd;
    }

    public int getNrZile() {
        return nrZile;
    }

    public void setNrZile(int nrZile) {
        this.nrZile = nrZile;
    }
}
