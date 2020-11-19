/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Cliente extends Pessoa implements Serializable{
    
    private double salario;
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String email, String telefone, double salario) {
        super(id, nome, email, telefone);
        this.salario = salario;
    }

    public double getSalario() {
        
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
