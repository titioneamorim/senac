/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Aluno;
import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Telefone;
import br.com.utilitario.UtilGerador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Titione
 */
public class AlunoDaoImplTest {

    private Aluno aluno;
    private AlunoDaoImpl alunoDaoImpl;

    public AlunoDaoImplTest() {
        alunoDaoImpl = new AlunoDaoImpl();
    }

    @Test
    public void testSalvar() throws Exception {
        System.out.println("Teste de criação de aluno: ");

        List<Telefone> telefones = new ArrayList<>();

        aluno = new Aluno(
                UtilGerador.gerarCaracter(8), //matricula
                null, //id
                UtilGerador.gerarNome(), //nome
                UtilGerador.gerarEmail(), //email
                UtilGerador.gerarNumero(11), //cpf
                UtilGerador.gerarNumero(8) //rg 
        );

        for (int i = 0; i < 2; i++) {
            telefones.add(gerarTelefone());
        }
        aluno.setTelefones(telefones);
        aluno.setEndereco(gerarEndereco());
        alunoDaoImpl.salvar(aluno);
        mostraAluno(aluno);
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
                "Meu bairro só tem casa"
        );
        return endereco;
    }

    // @Test
    public void testPesquisarPorId() throws Exception {
        System.out.println("Realizando pesquisa de aluno por ID ");
        System.out.println(" ");
        aluno = alunoDaoImpl.pesquisarPorId(10);
        mostraAluno(aluno);
        System.out.println("Pesquisa encerrada.");
    }

    // @Test
    public void testPesquisarPorNome() throws Exception {
        System.out.println("Realizando pesquisa de aluno por nome ");
        System.out.println(" ");
        List<Aluno> alunos = alunoDaoImpl.pesquisarPorNome("N");
        for (Aluno alun : alunos) {
            mostraAluno(alun);
        }
        System.out.println("Pesquisa encerrada.");
    }

    private void mostraAluno(Aluno aluno) {
        System.out.println("Dados do aluno ");
        System.out.println(" ");
        System.out.println("Id Aluno: " + aluno.getId());
        System.out.println("Matricula do Aluno: " + aluno.getMatricula());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("E-mail: " + aluno.getEmail());
        System.out.println("CPF: " + aluno.getCpf());
        System.out.println("RG: " + aluno.getRg());
        System.out.println("");
        System.out.println("Dados do endereco do aluno");
        System.out.println("ID Endereco " + aluno.getEndereco().getId());
        System.out.println("Logradouro: " + aluno.getEndereco().getLogradouro());
        System.out.println("Numero: " + aluno.getEndereco().getNumero());
        System.out.println("Bairro: " + aluno.getEndereco().getBairro());
        System.out.println("Cidade: " + aluno.getEndereco().getCidade());
        System.out.println("Estado: " + aluno.getEndereco().getEstado());
        System.out.println("");
        for (Telefone telefone : aluno.getTelefones()) {
            System.out.println("---//------//---");
            System.out.println("ID Telefone: " + telefone.getId());
            System.out.println("Numero: " + telefone.getNumero());
            System.out.println("Operadora : " + telefone.getOperadora());
            System.out.println("Tipo : " + telefone.getTipo());
        }
        System.out.println("");

    }

//    @Test
    public void testAlterar() throws Exception {
        System.out.println("Testando alterar Aluno");
        aluno = alunoDaoImpl.pesquisarPorId(4);
        aluno.setNome("Pedro");
        aluno.setRg("123456");
        for (int i = 0; i < aluno.getTelefones().size(); i++) {
            aluno.getTelefones().get(i).setOperadora(UtilGerador.gerarNome() + " Telecom");
        }
        alunoDaoImpl.alterar(aluno);
        System.out.println("");
        System.out.println("Aluno Alterado");
        System.out.println("");
        mostraAluno(aluno);
    }

    @Test
    public void testExcluirAluno() throws SQLException {
        int idExcluir = 3;
        System.out.println("excluirAluno");
        alunoDaoImpl.excluir(idExcluir);
        System.out.println("");
        System.out.println("Aluno com id " + idExcluir + " excluido com sucesso");
    }

}
