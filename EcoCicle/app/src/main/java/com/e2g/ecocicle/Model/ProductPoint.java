package com.e2g.ecocicle.Model;

import java.util.Collection;

/**
 * Created by tigrao on 06/03/15.
 */
public class ProductPoint {

    private Long idProdutoPonto;

    private Float price;

    private Float ecocoin;

    private Collection<Operation> operationCollection;

    private Product productidProduto;

    private PontoTroca pontoTrocaidPontoColeta;

    public ProductPoint() {
    }

    public ProductPoint(Long idProdutoPonto) {
        this.idProdutoPonto = idProdutoPonto;
    }

    public Long getIdProdutoPonto() {
        return idProdutoPonto;
    }

    public void setIdProdutoPonto(Long idProdutoPonto) {
        this.idProdutoPonto = idProdutoPonto;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getEcocoin() {
        return ecocoin;
    }

    public void setEcocoin(Float ecocoin) {
        this.ecocoin = ecocoin;
    }

    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    public Product getProductidProduto() {
        return productidProduto;
    }

    public void setProductidProduto(Product productidProduto) {
        this.productidProduto = productidProduto;
    }

    public PontoTroca getPontoTrocaidPontoColeta() {
        return pontoTrocaidPontoColeta;
    }

    public void setPontoTrocaidPontoColeta(PontoTroca pontoTrocaidPontoColeta) {
        this.pontoTrocaidPontoColeta = pontoTrocaidPontoColeta;
    }
}
