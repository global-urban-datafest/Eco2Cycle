package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 18/03/15.
 */
public class ProdutEco {
    private Long idProdutoPonto;
    private String name;
    private String description;
    private Double ecocoin;
    private String img;

    public ProdutEco() {
    }

    public Long getIdProdutoPonto() {
        return idProdutoPonto;
    }

    public void setIdProdutoPonto(Long idProdutoPonto) {
        this.idProdutoPonto = idProdutoPonto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEcocoin() {
        return ecocoin;
    }

    public void setEcocoin(Double ecocoin) {
        this.ecocoin = ecocoin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
