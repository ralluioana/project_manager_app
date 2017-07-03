package com.example.echipe;

import com.example.user.UserModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "echipe")
public class EchipaModel extends Object {

    @Id
    private String numeEchipa;

    private String manager;
    private String coechipieri="";

    public EchipaModel()
    {

    }

    public EchipaModel(String numeEchipa, String manager) {
        this.numeEchipa = numeEchipa;
        this.manager = manager;
    }

    public EchipaModel(String numeEchipa, String manager, String coechipieri) {
        this.numeEchipa = numeEchipa;
        this.manager = manager;
        this.coechipieri = coechipieri;
    }

    public EchipaModel(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getCoechipieri() {
        return coechipieri;
    }

    public void setCoechipieri(String coechipieri) {
        this.coechipieri = coechipieri;
    }

}
