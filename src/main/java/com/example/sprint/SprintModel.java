package com.example.sprint;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "sprint")
public class SprintModel {

    @Id
    private String numarSprint="0";

    private LocalDateTime dataStart;
    private LocalDateTime dataEnd;
    private int nrZile;

    public SprintModel(){
    }

    public SprintModel(String numarSprint, int nrZile) {
        this.numarSprint = numarSprint;
        this.nrZile = nrZile;
    }

    public SprintModel(String numarSprint, LocalDateTime dataStart, LocalDateTime dataEnd, int nrZile) {
        this.numarSprint = numarSprint;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.nrZile = nrZile;
    }

    public String getNumarSprint() {
        return numarSprint;
    }

    public void setNumarSprint(String numarSprint) {
        this.numarSprint = numarSprint;
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
