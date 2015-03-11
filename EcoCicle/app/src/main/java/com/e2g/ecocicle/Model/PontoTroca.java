package com.e2g.ecocicle.Model;

import java.util.Collection;

/**
 * Created by tigrao on 10/03/15.
 */
public class PontoTroca {

    private Long idPontoColeta;

    private String descricao;

    private String responsavel;

    private String endereco;

    private Float latitude;

    private Float longitude;

    private String login;

    private String password;

    private String tipo;

    private String ativo;

    private String phone;

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

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
