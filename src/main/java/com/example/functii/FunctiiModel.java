package com.example.functii;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "functii")
public class FunctiiModel extends Object {

    @Id
    private String numeFunctie="";


    public FunctiiModel()
    {

    }

    public String getNumeFunctie() {
        return numeFunctie;
    }

    public void setNumeFunctie(String numeFunctie) {
        this.numeFunctie = numeFunctie;
    }

    public FunctiiModel(String numeFunctie) {
        this.numeFunctie = numeFunctie;
    }
}
