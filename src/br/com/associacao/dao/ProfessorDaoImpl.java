/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Professor;
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
 * @author HP
 */
public class ProfessorDaoImpl implements Serializable {

    private Connection conexao;
    private PreparedStatement preparando;
    private ResultSet resultSet;

    public void salvar(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor(nome, cpf, numeroCracha) VALUES(?, ?, ?)";

        try {
            conexao = FabricaConexao.abrirConexao();
            //preparando = conexao.prepareStatement(sql); padrão usado
            preparando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //utiliza quando usar outra tabela para gerar o id quando salvar
            preparando.setString(1, professor.getNome());
            preparando.setString(2, professor.getCpf());
            preparando.setString(3, professor.getNumeroCracha());
            preparando.executeUpdate();
            resultSet = preparando.getGeneratedKeys(); //utilizar quando usar outra tabela para pegar a chave primaria
            resultSet.next(); //acessar resultset para verificar se tem registro
            professor.setId(resultSet.getInt(1)); //retorna o primeiro valor de resultset

            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();
            telefoneDaoImpl.salvarTelefoneProfessor(professor.getTelefones(), professor.getId(), conexao);

        } catch (SQLException eSQL) {
            System.err.println("Erro ao salvar professor " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }
    public void alterar(Professor professor) throws SQLException {
        String sql = "UPDATE professor SET nome = ?, cpf = ?, numeroCracha = ? WHERE id = ?";

        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, professor.getNome());
            preparando.setString(2, professor.getCpf());
            preparando.setString(3, professor.getNumeroCracha());
            preparando.setInt(4, professor.getId());
            preparando.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar " + e.getMessage());

        } finally {
            FabricaConexao.fecharConexao(conexao, preparando);
        }
    }

    public void excluir(Integer id) throws SQLException {
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement("DELETE FROM professor WHERE id = ?");
            preparando.setInt(1, id);
            preparando.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando);
        }
    }

    public Professor pesquisarPorId(Integer id) throws SQLException {
        Professor professor = null;
        String consulta = "SELECT * FROM professor  WHERE id = ?";

        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setInt(1, id);
            resultSet = preparando.executeQuery();
            if (resultSet.next()) {
                professor = new Professor();
                professor.setId(id);
                professor.setNome(resultSet.getString("nome"));
                professor.setCpf(resultSet.getString("cpf"));
                professor.setNumeroCracha(resultSet.getString("numeroCracha"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }

        return professor;
    }

    public List<Professor> pesquisarPorNome(String nome) throws SQLException {
        String consulta = "SELECT * FROM cliente  WHERE nome LIKE ?";
        Professor professor;
        List<Professor> professores = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();
            while (resultSet.next()) {
                professor = new Professor();
                professor.setId(resultSet.getInt("id"));
                professor.setNome(resultSet.getString("nome"));
                professor.setCpf(resultSet.getString("cpf"));
                professor.setNumeroCracha(resultSet.getString("numeroCracha"));
                professores.add(professor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por nome +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }

        return professores;
    }
}
