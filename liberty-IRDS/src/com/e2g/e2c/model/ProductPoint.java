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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ProductPoint")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "ProductPoint.findAll", query = "SELECT p FROM ProductPoint p"),
   @NamedQuery(name = "ProductPoint.findByIdProdutoPonto", query = "SELECT p FROM ProductPoint p WHERE p.idProdutoPonto = :idProdutoPonto"),
   @NamedQuery(name = "ProductPoint.findByPrice", query = "SELECT p FROM ProductPoint p WHERE p.price = :price"),
   @NamedQuery(name = "ProductPoint.findByPoint", query = "SELECT p FROM ProductPoint p WHERE p.pontoTrocaidPontoColeta.idPontoColeta = :idPonto"),
   @NamedQuery(name = "ProductPoint.findByEcocoin", query = "SELECT p FROM ProductPoint p WHERE p.ecocoin = :ecocoin")})
public class ProductPoint implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Basic(optional = false)
   @Column(name = "idProdutoPonto")
   private Long idProdutoPonto;
   // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Column(name = "price")
   private Float price;
   @Column(name = "ecocoin")
   private Float ecocoin;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "productPointidProdutoPonto")
   private Collection<Operation> operationCollection;
   @JoinColumn(name = "Product_idProduto", referencedColumnName = "idProduto")
   @ManyToOne(optional = false)
   private Product productidProduto;
   @JoinColumn(name = "PontoTroca_idPontoColeta", referencedColumnName = "idPontoColeta")
   @ManyToOne(optional = false)
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

   @XmlTransient
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

   @Override
   public int hashCode() {
       int hash = 0;
       hash += (idProdutoPonto != null ? idProdutoPonto.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof ProductPoint)) {
           return false;
       }
       ProductPoint other = (ProductPoint) object;
       if ((this.idProdutoPonto == null && other.idProdutoPonto != null) || (this.idProdutoPonto != null && !this.idProdutoPonto.equals(other.idProdutoPonto))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
	   return String.format("{\"idProdutoPonto\": \"%d\"," +
               "\"pontoTrocaidPontoColeta\": \"%d\"," +
               "\"productidProduto\": \"%d\"," +
               "\"price\": \"%.2f\"," +
               "\"ecocoin\": \"%.2f\"" +
               "}",
	            this.idProdutoPonto!=null?idProdutoPonto:null, 
        		this.pontoTrocaidPontoColeta!=null?pontoTrocaidPontoColeta.getIdPontoColeta():null,
				this.productidProduto!=null?productidProduto.getIdProduto():null,
				this.price!=null?price:null,
				this.ecocoin!=null?ecocoin:null);
   }
   
   public String toSell(){
	   return String.format("{\"idProdutoPonto\": \"%d\"," +
               "\"name\": \"%s\"," +
               "\"description\": \"%s\"," +
               "\"ecocoin\": \"%.2f\"," +
               "\"img\": \"%s\"" +
               "}",
	            this.idProdutoPonto!=null?idProdutoPonto:null, 
        		this.productidProduto.getProduct()!=null?this.productidProduto.getProduct():null,
				this.productidProduto.getMaterial()!=null?this.productidProduto.getMaterial():null,
				this.ecocoin!=null?ecocoin:null,
				this.productidProduto.getImg()!=null?this.productidProduto.getImg():null);
   }
   
}

