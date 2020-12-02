/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Cliente;
import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Funcionario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Silvio
 */
public class FuncionarioDaoImpl extends PessoaDaoImpl implements Serializable {

    public void salvar(Funcionario funcionario) throws SQLException {
        super.salvar(funcionario);
        String sql = "INSERT INTO funcionario(numeroCracha, idPessoa) VALUES(?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, funcionario.getNumeroCracha());
            preparando.setInt(2, funcionario.getId());
            preparando.executeUpdate();

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarEnderecoFuncionario(funcionario.getEndereco(), funcionario.getId(), conexao);
        } catch (SQLException eSQL) {
            System.err.println("Erro ao salvar cliente " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Funcionario funcionario) throws SQLException {
        super.alterar(funcionario);
        String sql = "UPDATE funcionario SET numeroCracha = ? WHERE idPessoa = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, funcionario.getNumeroCracha());
            preparando.setInt(2, funcionario.getId());
            preparando.executeUpdate();

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.alterarEndereco(funcionario.getEndereco(), conexao);
        } catch (SQLException eSQL) {
            System.err.println("Erro ao salvar cliente " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public Funcionario pesquisarPorId(Integer id) throws SQLException {
        Funcionario funcionario = null;
        String consulta = "SELECT * FROM funcionario f "
                + " INNER JOIN pessoa p on f.idPessoa = p.id"
                + " INNER JOIN endereco e on e.idFuncionario = f.idPessoa WHERE p.id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setInt(1, id);
            resultSet = preparando.executeQuery();
            if (resultSet.next()) {
                funcionario = new Funcionario();
                funcionario.setId(id);
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setEmail(resultSet.getString("email"));
                funcionario.setTelefone(resultSet.getString("telefone"));
                funcionario.setNumeroCracha(resultSet.getString("numeroCracha"));
                funcionario.setEndereco(new Endereco());
                funcionario.getEndereco().setId(resultSet.getInt("e.id"));
                funcionario.getEndereco().setLogradouro(resultSet.getString("logradouro"));
                funcionario.getEndereco().setNumero(resultSet.getString("numero"));
                funcionario.getEndereco().setBairro(resultSet.getString("bairro"));
                funcionario.getEndereco().setCidade(resultSet.getString("cidade"));
                funcionario.getEndereco().setEstado(resultSet.getString("estado"));
                funcionario.getEndereco().setCep(resultSet.getString("cep"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return funcionario;
    }

    public List<Funcionario> pesquisarPorNome(String nome) throws SQLException {
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<>();
        String consulta = "SELECT * FROM funcionario f "
                + " INNER JOIN pessoa p on f.idPessoa = p.id"
                + " INNER JOIN endereco e on e.idFuncionario = f.idPessoa WHERE p.nome LIKE ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();
            while (resultSet.next()) {
                funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("p.id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setEmail(resultSet.getString("email"));
                funcionario.setTelefone(resultSet.getString("telefone"));
                funcionario.setNumeroCracha(resultSet.getString("numeroCracha"));
                funcionario.setEndereco(new Endereco());
                funcionario.getEndereco().setId(resultSet.getInt("e.id"));
                funcionario.getEndereco().setLogradouro(resultSet.getString("logradouro"));
                funcionario.getEndereco().setNumero(resultSet.getString("numero"));
                funcionario.getEndereco().setBairro(resultSet.getString("bairro"));
                funcionario.getEndereco().setCidade(resultSet.getString("cidade"));
                funcionario.getEndereco().setEstado(resultSet.getString("estado"));
                funcionario.getEndereco().setCep(resultSet.getString("cep"));
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return funcionarios;
    }

}
