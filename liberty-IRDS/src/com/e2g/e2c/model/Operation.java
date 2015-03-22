package com.e2g.e2c.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
*
* @author guardezi
*/
@Entity
@Table(name = "Operation")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o"),
   @NamedQuery(name = "Operation.findByidPoint", query = "SELECT o FROM Operation o WHERE o.productPointidProdutoPonto.pontoTrocaidPontoColeta.idPontoColeta = :idPontoColeta"),
   @NamedQuery(name = "Operation.findByIdOperacao", query = "SELECT o FROM Operation o WHERE o.idOperacao = :idOperacao"),
   @NamedQuery(name = "Operation.findByPrice", query = "SELECT o FROM Operation o WHERE o.price = :price"),
   @NamedQuery(name = "Operation.findByClient", query = "SELECT o FROM Operation o WHERE o.clientidCliente = :idCliente"),
   @NamedQuery(name = "Operation.findByProductPoint", query = "SELECT o FROM Operation o WHERE o.productPointidProdutoPonto = :productPointidProdutoPonto"),
   @NamedQuery(name = "Operation.findByEcoCoin", query = "SELECT o FROM Operation o WHERE o.ecoCoin = :ecoCoin")})
public class Operation implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Basic(optional = false)
   @Column(name = "idOperacao")
   private Long idOperacao;
   @Column(name = "price")
   private Float price;
   @Column(name = "ecoCoin")
   private Float ecoCoin;
   
   @Column(name = "time")
   private Date time;
   @Column(name ="buy")
   private boolean buy;
   
   @JoinColumn(name = "ProductPoint_idProdutoPonto", referencedColumnName = "idProdutoPonto")
   @ManyToOne(optional = false)
   private ProductPoint productPointidProdutoPonto;
   @JoinColumn(name = "Client_idCliente", referencedColumnName = "idCliente")
   @ManyToOne(optional = false)
   private Client clientidCliente;
   
    

   
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

   public void setClientidCliente(Client clientidCliente) {
       this.clientidCliente = clientidCliente;
   }

   public Date getTime() {
	return time;
   }
   

	public void setTime(Date time) {
		this.time = time;
	}
	
	public boolean isBuy() {
		return buy;
	}

	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	@Override
   public int hashCode() {
       int hash = 0;
       hash += (idOperacao != null ? idOperacao.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof Operation)) {
           return false;
       }
       Operation other = (Operation) object;
       if ((this.idOperacao == null && other.idOperacao != null) || (this.idOperacao != null && !this.idOperacao.equals(other.idOperacao))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
	   return String.format("{\"idOperacao\": \"%d\"," +
           "\"price\": \"%s\"," +
           "\"ecoCoin\": \"%s\"," +
           "\"productPointidProdutoPonto\": \"%d\"," +
           "\"buy\": \"%s\"," +
           "\"clientidCliente\": \"%d\"" +
           "}",
            this.idOperacao!=null?idOperacao:null, 
    		String.valueOf(this.price!=null?price:null),
    		String.valueOf(this.ecoCoin!=null?ecoCoin:null),
			this.productPointidProdutoPonto!=null?productPointidProdutoPonto.getIdProdutoPonto():null,
			String.valueOf(buy),
			this.clientidCliente!=null?clientidCliente.getIdCliente():null);
   }
   
   
   
   public String toStatment() {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   return String.format("{\"idOperacao\": \"%d\"," +
           "\"point\": \"%s\"," +
           "\"material\": \"%s\"," +
           "\"product\": \"%s\"," +
           "\"price\": \"%.2f\"," +
           "\"ecocoin\": \"%.2f\"," +
           "\"buy\": \"%s\"," +
           "\"date\": \"%s\"" +
           "}",
            this.idOperacao!=null?idOperacao:null, 
    		this.productPointidProdutoPonto!=null?productPointidProdutoPonto.getPontoTrocaidPontoColeta().getDescricao():null,
			this.productPointidProdutoPonto!=null?productPointidProdutoPonto.getProductidProduto().getMaterial():null,
			this.productPointidProdutoPonto!=null?productPointidProdutoPonto.getProductidProduto().getProduct():null,
			this.price!=null?price:null,
			this.ecoCoin!=null?ecoCoin:null,
			String.valueOf(buy),
			this.time!=null?sdf.format(time):null);
   }
}

