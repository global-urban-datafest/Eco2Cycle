package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 06/03/15.
 */
public class Client {
    private int idCliente;
    private String rg;
    private String cpf;
    private String login;
    private String pass;
    private String adress;

    public Client() {
    }

    public Client(String pass, String login) {
        this.pass = pass;
        this.login = login;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
