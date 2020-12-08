/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Pessoa;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Titione
 */
public class PessoaDaoImpl implements Serializable {

    protected Connection conexao;
    protected PreparedStatement preparando;
    protected ResultSet resultSet;

    public void salvar(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa(nome, rg, cpf, email) VALUES(?, ?, ?, ?)";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparando.setString(1, pessoa.getNome());
            preparando.setString(2, pessoa.getRg());
            preparando.setString(3, pessoa.getCpf());
            preparando.setString(4, pessoa.getEmail());
            preparando.executeUpdate();
            resultSet = preparando.getGeneratedKeys();
            resultSet.next();
            pessoa.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            System.err.println("Erro ao salvar Pessoa " + e.getMessage());
        }
    }

    public void alterar(Pessoa pessoa) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, rg = ?, cpf = ?, email = ? WHERE id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, pessoa.getNome());
            preparando.setString(2, pessoa.getRg());
            preparando.setString(3, pessoa.getCpf());
            preparando.setString(4, pessoa.getEmail());
            preparando.setInt(5, pessoa.getId());
            preparando.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar pessoa " + e.getMessage());
        }
    }

    public void excluir(Integer id) throws SQLException {
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement("DELETE FROM pessoa WHERE id = ?");
            preparando.setInt(1, id);
            preparando.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando);
        }
    }

}
