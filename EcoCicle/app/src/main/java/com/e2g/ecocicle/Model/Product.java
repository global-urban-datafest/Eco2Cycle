package com.e2g.ecocicle.Model;

import java.util.Collection;

/**
 * Created by tigrao on 06/03/15.
 */
public class Product {

    private Long idProduto;

    private String material;

    private String product;

    private Float price;

    private Float ecocoin;

    private String unity;

    private Collection<ProductPoint> productPointCollection;

    public Product() {
    }

    public Product(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
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

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public Collection<ProductPoint> getProductPointCollection() {
        return productPointCollection;
    }

    public void setProductPointCollection(Collection<ProductPoint> productPointCollection) {
        this.productPointCollection = productPointCollection;
    }
}
