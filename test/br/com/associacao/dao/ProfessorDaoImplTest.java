/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Professor;
import br.com.associacao.entidade.Telefone;
import br.com.utilitario.UtilGerador;
import java.sql.SQLException;
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

      @Test
    public void testSalvar() throws Exception {
        System.out.println("Teste de criação de professor: ");

        List<Telefone> telefones = new ArrayList<>();

        professor = new Professor(
                UtilGerador.gerarCaracter(8),
                null,
                UtilGerador.gerarNome(),
                UtilGerador.gerarEmail(),
                UtilGerador.gerarNumero(11),
                UtilGerador.gerarNumero(8)
        );

        for (int i = 0; i < 2; i++) {
            telefones.add(gerarTelefone());
        }
        professor.setTelefones(telefones);
        professor.setEndereco(gerarEndereco());
        professorDaoImpl.salvar(professor);
        mostraProfessor(professor);
    }

  //  @Test
    public void testAlterar() throws Exception {
        System.out.println("Testando alterar professor");
        professor = professorDaoImpl.pesquisarPorId(25);
        professor.setNome("Pedro");
        professor.setRg("123456");
        for (int i = 0; i < professor.getTelefones().size(); i++) {
            professor.getTelefones().get(i).setOperadora(UtilGerador.gerarNome() + " Telecom");
        }
        professorDaoImpl.alterar(professor);
        System.out.println("");
        System.out.println("Professor Alterado");
        System.out.println("");
        mostraProfessor(professor);
    }

    //@Test
    public void testPesquisarPorId() throws Exception {
        System.out.println("Realizando pesquisa de professor por ID ");
        System.out.println(" ");
        professor = professorDaoImpl.pesquisarPorId(30);
        mostraProfessor(professor);
        System.out.println("Pesquisa encerrada.");
    }

    //   @Test
    public void testPesquisarPorNome() throws Exception {
        System.out.println("Realizando pesquisa de professor por nome ");
        System.out.println(" ");
        List<Professor> professores = professorDaoImpl.pesquisarPorNome("Junior");
        for (Professor prof : professores) {
            mostraProfessor(prof);
        }
        System.out.println("Pesquisa encerrada.");
    }

//    @Test
    public void testExcluirProfessor() throws SecurityException, SQLException {
        int idExcluir = 3;
        System.out.println("excluirAluno");
        professorDaoImpl.excluir(30);
        System.out.println("");
        System.out.println("Professor com id " + idExcluir + " excluido com sucesso");
    }

    private Telefone gerarTelefone() {
        Telefone telefone = new Telefone(
                Integer.parseInt(UtilGerador.gerarNumero(3)),
                UtilGerador.gerarCaracter(4),
                UtilGerador.gerarTelefoneFixo(),
                UtilGerador.gerarCaracter(3));

        return telefone;
    }

    private Endereco gerarEndereco() {
        Endereco endereco = new Endereco(
                Integer.parseInt(UtilGerador.gerarNumero(3)),
                UtilGerador.gerarCaracter(7),
                UtilGerador.gerarCaracter(4),
                UtilGerador.gerarNome(),
                UtilGerador.gerarCidade(),
                UtilGerador.gerarNome(),
                "Meu bairro é nobre! "
        );
        return endereco;
    }

    private void mostraProfessor(Professor professor) {
        System.out.println("Dados do professor ");
        System.out.println(" ");
        System.out.println("Id Aluno: " + professor.getId());
        System.out.println("Cracha do professor: " + professor.getCracha());
        System.out.println("Nome: " + professor.getNome());
        System.out.println("E-mail: " + professor.getEmail());
        System.out.println("CPF: " + professor.getCpf());
        System.out.println("RG: " + professor.getRg());
        System.out.println("");
        System.out.println("Dados do endereco do professor");
        System.out.println("ID Endereco " + professor.getEndereco().getId());
        System.out.println("Logradouro: " + professor.getEndereco().getLogradouro());
        System.out.println("Numero: " + professor.getEndereco().getNumero());
        System.out.println("Bairro: " + professor.getEndereco().getBairro());
        System.out.println("Cidade: " + professor.getEndereco().getCidade());
        System.out.println("Estado: " + professor.getEndereco().getEstado());
        System.out.println("");
        for (Telefone telefone : professor.getTelefones()) {
            System.out.println("---//------//---");
            System.out.println("ID Telefone: " + telefone.getId());
            System.out.println("Numero: " + telefone.getNumero());
            System.out.println("Operadora : " + telefone.getOperadora());
            System.out.println("Tipo : " + telefone.getTipo());
        }
        System.out.println("");

    }
}
