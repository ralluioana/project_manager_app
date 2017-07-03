package com.example.user;

import javax.persistence.*;

@Entity
@Table(name = "utilizatori")
public class UserModel {

    @Id
    private String userName;

    private String password;

    private String nume;
    private String prenume;
    private String email="";
    private String echipa="";
    private Boolean scrumMaster=false;
    private String functie="";
    private int orePeZi=0;

    public UserModel(String userName, String password, String nume, String prenume, String email, String echipa, Boolean scrumMaster, String functie, int orePeZi) {
        this.userName = userName;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.echipa = echipa;
        this.scrumMaster = scrumMaster;
        this.functie = functie;
        this.orePeZi = orePeZi;
    }

    public int getOrePeZi() {
        return orePeZi;
    }

    public void setOrePeZi(int orePeZi) {
        this.orePeZi = orePeZi;
    }

    public String getFunctie() {
        if(functie==null)
            return "";
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public Boolean getScrumMaster() {
        if(scrumMaster ==null)
            return false;
        return scrumMaster;
    }

    public void setScrumMaster(Boolean scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public String getEchipa() {
    if(echipa==null)
    {
        return "";
    }
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel(){}
    public UserModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword( )
    {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


}
