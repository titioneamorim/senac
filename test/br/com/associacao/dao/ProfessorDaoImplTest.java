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
 * @author HP
 */
public class ProfessorDaoImplTest {
    
    private Professor professor;
    private ProfessorDaoImpl professorDaoImpl;
    
    public ProfessorDaoImplTest() {
    }

    @Test
    public void testSalvar() throws Exception {
        System.out.println("salvar");
        professor = new Professor(
           null,
                UtilGerador.gerarNome(),
                UtilGerador.gerarNumero(3) + "." + UtilGerador.gerarNumero(3) + "."
                + UtilGerador.gerarNumero(3) + "-" + UtilGerador.gerarNumero(2),
                UtilGerador.gerarNumero(6));
        
        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            telefones.add(gerarTelefone());
        }
        
        professor.setTelefones(telefones);
        professorDaoImpl = new ProfessorDaoImpl();
        
        professorDaoImpl.salvar(professor);
    }
    
    private Telefone gerarTelefone(){
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
        fail("The test case is a prototype.");
    }

//    @Test
    public void testExcluir() throws Exception {
        System.out.println("excluir");
        Integer id = null;
        ProfessorDaoImpl instance = new ProfessorDaoImpl();
        instance.excluir(id);
        fail("The test case is a prototype.");
    }

//    @Test
    public void testPesquisarPorId() throws Exception {
        System.out.println("pesquisarPorId");
        Integer id = null;
        ProfessorDaoImpl instance = new ProfessorDaoImpl();
        Professor expResult = null;
        Professor result = instance.pesquisarPorId(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

//    @Test
    public void testPesquisarPorNome() throws Exception {
        System.out.println("pesquisarPorNome");
        String nome = "";
        ProfessorDaoImpl instance = new ProfessorDaoImpl();
        List<Professor> expResult = null;
        List<Professor> result = instance.pesquisarPorNome(nome);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
