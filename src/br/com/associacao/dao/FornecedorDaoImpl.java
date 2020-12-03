/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Fornecedor;
import br.com.associacao.entidade.Funcionario;
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
public class FornecedorDaoImpl extends PessoaDaoImpl implements Serializable {

    public void salvar(Fornecedor fornecedor) throws SQLException {
        super.salvar(fornecedor);
        String sql = "INSERT INTO fornecedor(cnpj, inscricaoEstadual, idPessoa) VALUES(?, ?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, fornecedor.getCnpj());
            preparando.setString(2, fornecedor.getInscricaoEstadual());
            preparando.setInt(3, fornecedor.getId());
            preparando.executeUpdate();
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            for (Endereco endereco : fornecedor.getEnderecos()) {
                enderecoDaoImpl.salvarEnderecoFornecedor(endereco, fornecedor.getId(), conexao);
            }
        } catch (SQLException eSQL) {
            System.out.println("Erro ao salvar fornecedor " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Fornecedor fornecedor) throws SQLException {
        super.alterar(fornecedor);
        String sql = "UPDATE fornecedor SET cnpj = ?, inscricaoEstadual = ? WHERE idPessoa = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, fornecedor.getCnpj());
            preparando.setString(2, fornecedor.getInscricaoEstadual());
            preparando.setInt(3, fornecedor.getId());
            preparando.executeUpdate();

            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();

            for (Endereco endereco : fornecedor.getEnderecos()) {
                enderecoDaoImpl.alterarEndereco(endereco, conexao);
            }
        } catch (SQLException eSQL) {
            System.err.println("Erro ao alterar fornecedor " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public Fornecedor pesquisarPorId(Integer id) throws SQLException {
        String consulta = "SELECT * FROM fornecedor f INNER JOIN pessoa p on f.idPessoa = p.id"
                + " INNER JOIN endereco e on e.idFornecedor = f.idPessoa WHERE p.id = ?";
        Fornecedor fornecedor = null;
        Endereco endereco;
        List<Endereco> enderecos;
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setInt(1, id);
            resultSet = preparando.executeQuery();
            if (resultSet.next()) {
                enderecos = new ArrayList<>();
                fornecedor = new Fornecedor();
                fornecedor.setId(id);
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setEmail(resultSet.getString("email"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setCnpj(resultSet.getString("cnpj"));
                fornecedor.setInscricaoEstadual(resultSet.getString("inscricaoEstadual"));
                do {
                    endereco = new Endereco();
                    endereco.setId(resultSet.getInt("e.id"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    endereco.setCep(resultSet.getString("cep"));
                    enderecos.add(endereco);
                } while (resultSet.next());
                fornecedor.setEnderecos(enderecos);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return fornecedor;
    }

    public List<Fornecedor> pesquisarPorNome(String nome) throws SQLException {
        String consulta = "SELECT * FROM fornecedor f INNER JOIN pessoa p on f.idPessoa = p.id"
                + " INNER JOIN endereco e on e.idFornecedor = f.idPessoa WHERE p.nome LIKE ?";
        Fornecedor fornecedor = null;
        Endereco endereco;
        List<Fornecedor> fornecedores = new ArrayList<>();
        List<Endereco> enderecos;
        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();
            int idFornecedor;
            int idAntigo = 0;
            while (resultSet.next()) {
                idFornecedor = resultSet.getInt("p.id");

                if (idFornecedor != idAntigo) {
                    fornecedor = new Fornecedor();
                    enderecos = new ArrayList<>();
                    fornecedor.setEnderecos(enderecos);

                    fornecedor.setId(idFornecedor);
                    fornecedor.setNome(resultSet.getString("nome"));
                    fornecedor.setEmail(resultSet.getString("email"));
                    fornecedor.setTelefone(resultSet.getString("telefone"));
                    fornecedor.setCnpj(resultSet.getString("cnpj"));
                    fornecedor.setInscricaoEstadual(resultSet.getString("inscricaoEstadual"));
                    idAntigo = idFornecedor;
                    fornecedores.add(fornecedor);
                }
                endereco = new Endereco();
                endereco.setId(resultSet.getInt("e.id"));
                endereco.setLogradouro(resultSet.getString("logradouro"));
                endereco.setNumero(resultSet.getString("numero"));
                endereco.setBairro(resultSet.getString("bairro"));
                endereco.setCidade(resultSet.getString("cidade"));
                endereco.setEstado(resultSet.getString("estado"));
                endereco.setCep(resultSet.getString("cep"));
                fornecedor.getEnderecos().add(endereco);

            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return fornecedores;
    }

}
