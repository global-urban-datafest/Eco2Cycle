package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 13/03/15.
 */
public class InfLevel {
    private String tipoNome;
    private String Level;
    private String next;
    private Double xp;

    public InfLevel() {
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Double getXp() {
        return xp;
    }

    public void setXp(Double xp) {
        this.xp = xp;
    }

    public String getTipoNome() {
        return tipoNome;
    }

    public void setTipoNome(String tipoNome) {
        this.tipoNome = tipoNome;
    }
}
