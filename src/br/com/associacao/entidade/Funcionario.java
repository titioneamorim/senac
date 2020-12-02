/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

import java.io.Serializable;

/**
 *
 * @author Silvio
 */
public class Funcionario extends Pessoa implements Serializable{
    
    private String numeroCracha;
    private Endereco endereco;

    public Funcionario() {
    }

    public Funcionario(Integer id, String nome, String email, String telefone, String numeroCracha) {
        super(id, nome, email, telefone);
        this.numeroCracha = numeroCracha;
    }

    public String getNumeroCracha() {
        return numeroCracha;
    }

    public void setNumeroCracha(String numeroCracha) {
        this.numeroCracha = numeroCracha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
        
}
