package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 20/03/15.
 */
public class OperationControl {

    private Long idOperacao;
    private String point;
    private String product;
    private Double ecocoin;
    private Boolean buy;
    private String date;

    public OperationControl() {
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Double getEcocoin() {
        return ecocoin;
    }

    public void setEcocoin(Double ecocoin) {
        this.ecocoin = ecocoin;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
