/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Professor;
import br.com.associacao.entidade.Telefone;
import br.com.utilitario.UtilGerador;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Titione
 */
public class ProfessorDaoImplTest {

    private Professor professor;
    private ProfessorDaoImpl professorDaoImpl;

    public ProfessorDaoImplTest() {
        professorDaoImpl = new ProfessorDaoImpl();
    }

 //  @Test
    public void testSalvar() throws Exception {
        System.out.println("salvar");
        professor = new Professor(
                null,
                UtilGerador.gerarNome(),
                UtilGerador.gerarNumero(3) + "." + UtilGerador.gerarNumero(3) + "."
                + UtilGerador.gerarNumero(3) + "-" + UtilGerador.gerarNumero(2),
                UtilGerador.gerarNumero(6));

        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            telefones.add(gerarTelefone());
        }
        professor.setTelefones(telefones);
        professorDaoImpl.salvar(professor);
    }

    private Telefone gerarTelefone() {
        Telefone tel = new Telefone(
                null,
                "Fixo",
                UtilGerador.gerarTelefoneFixo(),
                "Vivo");

        return tel;
    }

//    @Test
    public void testAlterar() throws Exception {
        System.out.println("alterar");
        Professor professor = null;
        ProfessorDaoImpl instance = new ProfessorDaoImpl();
        instance.alterar(professor);
    }

//    @Test
    public void testExcluir() throws Exception {
        System.out.println("excluir");
        Integer id = null;
        ProfessorDaoImpl instance = new ProfessorDaoImpl();
        instance.excluir(id);
    }

 //   @Test
    public void testPesquisarPorId() throws Exception {
        System.out.println("pesquisarPorId");
        professor = professorDaoImpl.pesquisarPorId(2);
        mostraProfessor();
    }

    @Test
    public void testPesquisarPorNome() throws Exception {
        System.out.println("pesquisarPorNome");
        List<Professor> professores = professorDaoImpl.pesquisarPorNome("Kelly");
        for (Professor prof : professores) {
            professor = prof;
            mostraProfessor();
        }
        
    }

    public void mostraProfessor() {
        System.out.println("Id Professor: " + professor.getId());
        System.out.println("Professor: " + professor.getNome());
        System.out.println("CPF: " + professor.getCpf());
        System.out.println("Crachá: " + professor.getNumeroCracha());
        for (Telefone telefone : professor.getTelefones()) {
            System.out.println("");
            System.out.println("ID Telefone " + telefone.getId());
            System.out.println("Numero " + telefone.getNumero());
            System.out.println("Tipo " + telefone.getTipo());
            System.out.println("Operadora " + telefone.getOperadora());
        }
    }

}
