package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 10/03/15.
 */
public class Operation{
    private static final long serialVersionUID = 1L;

    private Long idOperacao;
    private Float price;
    private Float ecoCoin;
    private ProductPoint productPointidProdutoPonto;
    private Client clientidCliente;
    private Boolean buy;

    public Operation() {
    }

    public Operation(Long idOperacao) {
        this.idOperacao = idOperacao;
    }

    public Long getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Long idOperacao) {
        this.idOperacao = idOperacao;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getEcoCoin() {
        return ecoCoin;
    }

    public void setEcoCoin(Float ecoCoin) {
        this.ecoCoin = ecoCoin;
    }

    public ProductPoint getProductPointidProdutoPonto() {
        return productPointidProdutoPonto;
    }

    public void setProductPointidProdutoPonto(ProductPoint productPointidProdutoPonto) {
        this.productPointidProdutoPonto = productPointidProdutoPonto;
    }

    public Client getClientidCliente() {
        return clientidCliente;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    public void setClientidCliente(Client clientidCliente) {
        this.clientidCliente = clientidCliente;
    }
}

