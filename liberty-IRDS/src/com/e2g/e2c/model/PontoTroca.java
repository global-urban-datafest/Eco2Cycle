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
* @email jefersonguardezi@gmail.com
* 
* E2G eco to green
* 
*/
@Entity
@Table(name = "PontoTroca")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "PontoTroca.findAll", query = "SELECT p FROM PontoTroca p"),
   @NamedQuery(name = "PontoTroca.findByIdPontoColeta", query = "SELECT p FROM PontoTroca p WHERE p.idPontoColeta = :idPontoColeta"),
   @NamedQuery(name = "PontoTroca.findByDescricao", query = "SELECT p FROM PontoTroca p WHERE p.descricao = :descricao"),
   @NamedQuery(name = "PontoTroca.findByResponsavel", query = "SELECT p FROM PontoTroca p WHERE p.responsavel = :responsavel"),
   @NamedQuery(name = "PontoTroca.findByEndereco", query = "SELECT p FROM PontoTroca p WHERE p.endereco = :endereco"),
   @NamedQuery(name = "PontoTroca.findByLatitude", query = "SELECT p FROM PontoTroca p WHERE p.latitude = :latitude"),
   @NamedQuery(name = "PontoTroca.findByLongitude", query = "SELECT p FROM PontoTroca p WHERE p.longitude = :longitude")})
public class PontoTroca implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Basic(optional = false)
   @Column(name = "idPontoColeta")
   private Long idPontoColeta;
   @Column(name = "Descricao")
   private String descricao;
   @Column(name = "Responsavel")
   private String responsavel;
   @Column(name = "Endereco")
   private String endereco;
   @Column(name = "Latitude")
   private Float latitude;
   @Column(name = "Longitude")
   private Float longitude;
   @Column(name = "login")
   private String login;
   @Column(name = "password")
   private String password;
   @Column(name = "tipo")
   private String tipo;
   
   
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "pontoTrocaidPontoColeta")
   private Collection<ProductPoint> productPointCollection;

   public PontoTroca() {
   }

   public PontoTroca(Long idPontoColeta) {
       this.idPontoColeta = idPontoColeta;
   }

   public Long getIdPontoColeta() {
       return idPontoColeta;
   }

   public void setIdPontoColeta(Long idPontoColeta) {
       this.idPontoColeta = idPontoColeta;
   }

   public String getDescricao() {
       return descricao;
   }

   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }

   public String getResponsavel() {
       return responsavel;
   }

   public void setResponsavel(String responsavel) {
       this.responsavel = responsavel;
   }

   public String getEndereco() {
       return endereco;
   }

   public void setEndereco(String endereco) {
       this.endereco = endereco;
   }

   public Float getLatitude() {
       return latitude;
   }

   public void setLatitude(Float latitude) {
       this.latitude = latitude;
   }

   public Float getLongitude() {
       return longitude;
   }

   public void setLongitude(Float longitude) {
       this.longitude = longitude;
   }

   @XmlTransient
   public Collection<ProductPoint> getProductPointCollection() {
       return productPointCollection;
   }

   public void setProductPointCollection(Collection<ProductPoint> productPointCollection) {
       this.productPointCollection = productPointCollection;
   }
   

   public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}

@Override
   public int hashCode() {
       int hash = 0;
       hash += (idPontoColeta != null ? idPontoColeta.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof PontoTroca)) {
           return false;
       }
       PontoTroca other = (PontoTroca) object;
       if ((this.idPontoColeta == null && other.idPontoColeta != null) || (this.idPontoColeta != null && !this.idPontoColeta.equals(other.idPontoColeta))) {
           return false;
       }
       
       return true;
   }
   
   
   
   @Override
   public String toString() {
	   return String.format("{\"idPontoColeta\": \"%d\"," +
    		                "\"descricao\": \"%s\"," +
    		                "\"responsavel\": \"%s\"," +
    		                "\"endereco\": \"%s\"," +
    		                "\"tipo\": \"%s\"," +
    		                "\"latitude\": \"%f\"," +
    		                "\"longitude\": \"%f\"" +
    		                "}",
       		                idPontoColeta!=null?idPontoColeta:null, 
	                		descricao!=null?descricao:null,
            				responsavel!=null?responsavel:null,
    						endereco!=null?endereco:null,
							tipo!=null?tipo:null,
							latitude!=null?latitude:null,
							longitude!=null?longitude:null);
	   
   }
   
}
