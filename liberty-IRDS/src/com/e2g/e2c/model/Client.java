package com.e2g.e2c.model;

import java.io.Serializable;
import java.util.Collection;

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

@Entity
@Table(name = "Client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByIdCliente", query = "SELECT c FROM Client c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByAdress", query = "SELECT c FROM Client c WHERE c.adress = :adress"),
    @NamedQuery(name = "Client.findByRg", query = "SELECT c FROM Client c WHERE c.rg = :rg"),
    @NamedQuery(name = "Client.findByLogin", query = "SELECT c FROM Client c WHERE c.login = :login"),
    @NamedQuery(name = "Client.findByCpf", query = "SELECT c FROM Client c WHERE c.cpf = :cpf")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCliente")
    private Long idCliente;
    @Column(name = "Name")
    private String name;
    @Column(name = "Adress")
    private String adress;
    @Column(name = "RG")
    private String rg;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    
    @Column(name = "email")
    private String email;
    @Column(name = "ativo")
    private String ativo;
    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidCliente")
    private Collection<Operation> operationCollection;

    public Client() {
    }

    public Client(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    
    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return String.format("{\"idCliente\": \"%d\"," +
    			"\"name\":\"%s\","+
	            "\"rg\": \"%s\"," +
	            "\"cpf\": \"%s\"," +
	            "\"login\": \"%s\"," +
	            "\"pass\": \"%s\"," +
	            "\"email\": \"%s\"," +
	            "\"ativo\":\"%s\","+
	            "\"adress\": \"%s\"" +
            "}",
            this.idCliente!=null?idCliente:0,
    		this.name!=null?name:"null",
    		this.rg!=null?rg:"null",
			this.cpf!=null?cpf:"null",
			this.login!=null?login:"null",
			this.pass!=null?pass:"null",
			this.email!=null?email:"null",
			this.ativo!=null?ativo:"null",
			this.adress!=null?adress:"null");
    }
//    
//    public String recycledata name() {
//		
//	}
    
}




