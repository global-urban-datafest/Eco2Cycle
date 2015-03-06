package com.e2g.ecocicle.Model;

/**
 * Created by tigrao on 06/03/15.
 */
public class Product {
    private int idProduto;
    private String material;
    private String product;
    private Double price;
    private Double ecocoin;
    private String unity;

    public Product() {
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getEcocoin() {
        return ecocoin;
    }

    public void setEcocoin(Double ecocoin) {
        this.ecocoin = ecocoin;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}
