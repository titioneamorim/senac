/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.controle;

import br.com.associacao.entidade.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProfessorControle implements Serializable {

    private List<Professor> professores = new ArrayList<>();

    public void salvar(Professor professor) {
        professores.add(professor);
    }

    public Integer pegarUltimoId() {
        int tamanhoLista = professores.size();
        if(tamanhoLista == 0){
            return 0;
        }
        return professores.get(tamanhoLista - 1).getId();
    }
    
    public List<Professor> pesquisarPorNome(String nome){
        List<Professor> professoresPesquisado = new ArrayList<>();
        for (Professor professor : professores) {
            if(professor.getNome().contains(nome)){
                professoresPesquisado.add(professor);
            }
        }
        return professoresPesquisado;
    }

    
}
