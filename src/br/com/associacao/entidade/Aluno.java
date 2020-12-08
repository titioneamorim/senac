/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.entidade;

import java.util.List;

/**
 *
 * @author Titione
 */
public class Aluno extends Pessoa {

    String matricula;

    public Aluno(String matricula, Integer id, String nome, String email, String cpf, String rg) {
        super(id, nome, email, cpf, rg);
        this.matricula = matricula;
    }

    public Aluno() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
