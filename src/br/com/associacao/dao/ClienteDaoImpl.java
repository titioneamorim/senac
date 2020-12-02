/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Cliente;
import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Pessoa;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ClienteDaoImpl extends PessoaDaoImpl implements Serializable {


    @Override
    public void salvar(Pessoa pessoa) throws SQLException { 
        super.salvar(pessoa);
        Cliente cliente = (Cliente) pessoa; // Cast ou casting
        String sql = "INSERT INTO cliente(salario, idPessoa) VALUES(?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);            
            preparando.setDouble(1, cliente.getSalario());
            preparando.setInt(2, cliente.getId());
            preparando.executeUpdate();           
            
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarEnderecoCliente(cliente.getEndereco(), cliente.getId(), conexao);
            
        } catch (SQLException eSQL) {
            System.err.println("Erro ao salvar cliente " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Cliente cliente) throws SQLException {
        super.alterar(cliente);
        String sql = "UPDATE cliente SET salario = ? WHERE idPessoa = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setDouble(1, cliente.getSalario());
            preparando.setInt(2, cliente.getId());
            preparando.executeUpdate();
            
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.alterarEndereco(cliente.getEndereco(), conexao);
        } catch (SQLException e) {
            System.err.println("Erro ao alterar " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando);
        }
    }

    
    public Cliente pesquisarPorId(Integer id) throws SQLException {
        Cliente cliente = null;
        String consulta = "SELECT * FROM cliente c "
                + " INNER JOIN pessoa p on c.idPessoa = p.id"
                + " INNER JOIN endereco e on e.idCliente = c.idPessoa"
                + " WHERE p.id = ?";
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setInt(1, id);
            resultSet = preparando.executeQuery();
            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(id);
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setSalario(resultSet.getDouble("salario"));
                cliente.setEndereco(new Endereco());
                cliente.getEndereco().setId(resultSet.getInt("e.id"));
                cliente.getEndereco().setLogradouro(resultSet.getString("logradouro"));
                cliente.getEndereco().setNumero(resultSet.getString("numero"));
                cliente.getEndereco().setBairro(resultSet.getString("bairro"));
                cliente.getEndereco().setCidade(resultSet.getString("cidade"));
                cliente.getEndereco().setEstado(resultSet.getString("estado"));
                cliente.getEndereco().setCep(resultSet.getString("cep"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }

        return cliente;
    }

    public List<Cliente> pesquisarPorNome(String nome) throws SQLException {
        String consulta = "SELECT * FROM cliente c "
                + "inner join pessoa p on c.idPessoa = p.id "
                + "inner JOIN endereco e on e.idCliente = c.idPessoa "
                + " WHERE p.nome LIKE ?";
        Cliente cliente;
        Endereco endereco;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();
            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("p.id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setSalario(resultSet.getDouble("salario"));
                endereco = new Endereco();
                endereco.setId(resultSet.getInt("e.id"));
                endereco.setLogradouro(resultSet.getString("logradouro"));
                endereco.setNumero(resultSet.getString("numero"));
                endereco.setBairro(resultSet.getString("bairro"));
                endereco.setCidade(resultSet.getString("cidade"));
                endereco.setEstado(resultSet.getString("estado"));
                endereco.setCep(resultSet.getString("cep"));
                cliente.setEndereco(endereco);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por nome +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }

        return clientes;
    }
}
