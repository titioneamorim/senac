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
public class Professor {
    String cracha;
    String idPessoa;

    public Professor(String cracha, String idPessoa) {
        this.cracha = cracha;
        this.idPessoa = idPessoa;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

  
    public Professor() {
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
    }
    
}
