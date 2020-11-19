/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class Telefone implements Serializable {

    private Integer id;
    private String tipo;
    private String numero;
    private String operadora;

    public Telefone() {
    }

    public Telefone(Integer id, String tipo, String numero, String operadora) {
        this.id = id;
        this.tipo = tipo;
        this.numero = numero;
        this.operadora = operadora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    
}