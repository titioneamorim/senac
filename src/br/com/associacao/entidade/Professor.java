/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author HP
 */
public class Professor implements Serializable {

    private Integer id;
    private String nome;
    private String cpf;
    private String numeroCracha;
    private List<Telefone> telefones;

    public Professor() {
    }

    public Professor(Integer id, String nome, String cpf, String numeroCracha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.numeroCracha = numeroCracha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroCracha() {
        return numeroCracha;
    }

    public void setNumeroCracha(String numeroCracha) {
        this.numeroCracha = numeroCracha;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
    
}
