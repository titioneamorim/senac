/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

/**
 *
 * @author Titione
 */
public class Professor extends Pessoa {

    String cracha;

    public Professor() {
    }

    public Professor(String cracha, Integer id, String nome, String email, String cpf, String rg) {
        super(id, nome, email, cpf, rg);
        this.cracha = cracha;
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
    }

}
