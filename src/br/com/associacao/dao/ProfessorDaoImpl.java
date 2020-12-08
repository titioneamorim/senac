/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Pessoa;
import br.com.associacao.entidade.Professor;
import br.com.associacao.entidade.Telefone;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Titione
 */
public class ProfessorDaoImpl extends PessoaDaoImpl implements Serializable {

    public void salvar(Professor professor) throws SQLException {
        super.salvar(professor);
        String sql = "INSERT INTO professor(cracha, idPessoa) VALUES(?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, professor.getCracha());
            preparando.setInt(2, professor.getId());
            preparando.executeUpdate();
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarEnderecoAluno(professor.getEndereco(), professor.getId(), conexao);
            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();
            telefoneDaoImpl.salvarTelefone(professor.getTelefones(), professor.getId(), conexao);

        } catch (SQLException eSQL) {
            System.out.println("Erro ao salvar aluno: Erro = " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Professor professor) throws SQLException {
        super.alterar(professor);
        String sql = "UPDATE pessoa SET cpf = ?, nome = ? , rg = ?, email = ? WHERE id = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, professor.getCpf());
            preparando.setString(2, professor.getNome());
            preparando.setString(3, professor.getRg());
            preparando.setString(4, professor.getEmail());
            preparando.setInt(5, professor.getId());
            preparando.executeUpdate();

            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();

            for (Telefone telefone : professor.getTelefones()) {
                telefoneDaoImpl.alterar(telefone, conexao);
            }

        } catch (SQLException eSQL) {
            System.err.println("Erro ao alterar professor " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public Professor pesquisarPorId(Integer id) throws SQLException {
        String consulta = "SELECT * , e.id as idEndereco FROM professor pr "
                + "INNER JOIN pessoa p on pr.idPessoa = p.id "
                + "INNER JOIN endereco e on e.idPessoa = pr.idPessoa "
                + "INNER JOIN telefone t on pr.idPessoa = t.idPessoa WHERE p.id = ?";
        Professor professor = null;
        Endereco endereco;
        Telefone telefone;
        List<Telefone> telefones;
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setInt(1, id);
            resultSet = preparando.executeQuery();
            if (resultSet.next()) {
                telefones = new ArrayList<>();
                professor = new Professor();
                professor.setId(id);
                professor.setNome(resultSet.getString("nome"));
                professor.setEmail(resultSet.getString("email"));
                professor.setCpf(resultSet.getString("cpf"));
                professor.setRg(resultSet.getString("rg"));
                professor.setCracha(resultSet.getString("cracha"));
                professor.setTelefones(telefones);
                endereco = new Endereco();
                endereco.setId(resultSet.getInt("idEndereco"));
                endereco.setLogradouro(resultSet.getString("logradouro"));
                endereco.setNumero(resultSet.getString("numero"));
                endereco.setBairro(resultSet.getString("bairro"));
                endereco.setCidade(resultSet.getString("cidade"));
                endereco.setEstado(resultSet.getString("estado"));
                do {
                    telefone = new Telefone();
                    telefone.setId(resultSet.getInt("t.id"));
                    telefone.setNumero(resultSet.getString("numero"));
                    telefone.setTipo("tipo");
                    telefone.setOperadora("Vivo");
                    telefones.add(telefone);
                } while (resultSet.next());
                professor.setTelefones(telefones);
                professor.setEndereco(endereco);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id. Erro =  " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return professor;
    }

    public List<Professor> pesquisarPorNome(String nome) throws SQLException {
        String consulta = "SELECT * , e.id as idEndereco FROM professor pr "
                + "INNER JOIN pessoa p on pr.idPessoa = p.id "
                + "INNER JOIN endereco e on e.idPessoa = pr.idPessoa "
                + "INNER JOIN telefone t on pr.idPessoa = t.idPessoa WHERE p.nome LIKE ?";
        Professor professor = null;
        Endereco endereco;
        Telefone telefone;
        List<Professor> professores = new ArrayList<>();
        List<Telefone> telefones;

        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();

            int idProfessor;
            int idAntigo = 0;
            while (resultSet.next()) {
                idProfessor = resultSet.getInt("idPessoa");

                if (idProfessor != idAntigo) {
                    professor = new Professor();
                    telefones = new ArrayList<>();
                    professor.setTelefones(telefones);
                    professor.setId(resultSet.getInt("id"));
                    professor.setNome(resultSet.getString("nome"));
                    professor.setEmail(resultSet.getString("email"));
                    professor.setCpf(resultSet.getString("cpf"));
                    professor.setRg(resultSet.getString("rg"));
                    professor.setCracha(resultSet.getString("cracha"));
                    endereco = new Endereco();
                    endereco.setId(resultSet.getInt("idEndereco"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    idAntigo = idProfessor;
                    professor.setEndereco(endereco);
                    professores.add(professor);
                }
                telefone = new Telefone();
                telefone.setId(resultSet.getInt("t.id"));
                telefone.setNumero(resultSet.getString("numero"));
                telefone.setTipo("tipo");
                telefone.setOperadora("operadora");
                professor.getTelefones().add(telefone);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por nome = " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return professores;
    }

}
