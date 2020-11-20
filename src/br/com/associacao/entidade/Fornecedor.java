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
 * @author Admin
 */
public class Fornecedor extends Pessoa implements Serializable{
    
    private String cnpj;
    private String inscricaoEstadual;
    private List<Endereco> enderecos;

    public Fornecedor() {
    }

    public Fornecedor(Integer id, String cnpj, String inscricaoEstadual,
            String nome, String email, String telefone) {
        super(id, nome, email, telefone);
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

}
