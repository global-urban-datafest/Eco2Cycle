package com.e2g.e2c.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
*
* @author guardezi
*/
@Entity
@Table(name = "Product")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
   @NamedQuery(name = "Product.findByIdProduto", query = "SELECT p FROM Product p WHERE p.idProduto = :idProduto"),
   @NamedQuery(name = "Product.findByMaterial", query = "SELECT p FROM Product p WHERE p.material = :material"),
   @NamedQuery(name = "Product.findByProduct", query = "SELECT p FROM Product p WHERE p.product = :product"),
   @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
   @NamedQuery(name = "Product.findByEcocoin", query = "SELECT p FROM Product p WHERE p.ecocoin = :ecocoin"),
   @NamedQuery(name = "Product.findByUnity", query = "SELECT p FROM Product p WHERE p.unity = :unity")})
public class Product implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Basic(optional = false)
   @Column(name = "idProduto")
   private Long idProduto;
   @Column(name = "material")
   private String material;
   @Column(name = "product")
   private String product;
   // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Column(name = "price")
   private Float price;
   @Column(name = "ecocoin")
   private Float ecocoin;
   @Column(name = "unity")
   private String unity;
   @Column(name ="buy")
   private boolean buy;
   
   @Column(name = "img")
   private String img;
   
   
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "productidProduto")
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

   public boolean isBuy() {
	return buy;
}

public void setBuy(boolean buy) {
	this.buy = buy;
}

@XmlTransient
   public Collection<ProductPoint> getProductPointCollection() {
       return productPointCollection;
   }

   public void setProductPointCollection(Collection<ProductPoint> productPointCollection) {
       this.productPointCollection = productPointCollection;
   }
   
   

   public String getImg() {
	return img;
}

public void setImg(String img) {
	this.img = img;
}

@Override
   public int hashCode() {
       int hash = 0;
       hash += (idProduto != null ? idProduto.hashCode() : 0);
       return hash;
   }

   
   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof Product)) {
           return false;
       }
       Product other = (Product) object;
       if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
       return String.format("{\"idProduto\": \"%d\"," +
               "\"material\": \"%s\"," +
               "\"product\": \"%s\"," +
               "\"price\": \"%.2f\"," +
               "\"ecocoin\": \"%.2f\"," +
               "\"buy\": \"%s\"," +
               "\"img\": \"%s\"," +
               "\"unity\": \"%s\"" +"}",
                idProduto!=null?idProduto:null, 
        		material!=null?material:null,
				product!=null?product:null,
				price!=null?price:null,
				ecocoin!=null?ecocoin:null,
				String.valueOf(buy),
				img!=null?img:null,
				unity!=null?unity:null);

   }
   
}
