/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Aluno;
import br.com.associacao.entidade.Endereco;
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
public class AlunoDaoImpl extends PessoaDaoImpl implements Serializable {

    public void salvar(Aluno aluno) throws SQLException {
        super.salvar(aluno);
        String sql = "INSERT INTO aluno(matricula, idPessoa) VALUES(?, ?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, aluno.getMatricula());
            preparando.setInt(2, aluno.getId());
            preparando.executeUpdate();
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
                enderecoDaoImpl.salvarEnderecoAluno(aluno.getEndereco(), aluno.getId(), conexao);
        } catch (SQLException eSQL) {
            System.out.println("Erro ao salvar aluno: Erro = " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Aluno aluno) throws SQLException {
        super.alterar(aluno);
        String sql = "UPDATE aluno SET matricula = ? WHERE idPessoa = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, aluno.getMatricula());
            preparando.setInt(2, aluno.getId());
            preparando.executeUpdate();

            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();
            for (Telefone telefone : aluno.getTelefones()) {
                telefoneDaoImpl.alterar(telefone, aluno.getId(), conexao);
            }

        } catch (SQLException eSQL) {
            System.err.println("Erro ao alterar fornecedor " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public Aluno pesquisarPorId(Integer id) throws SQLException {
        String consulta = "SELECT * FROM aluno a "
                + "INNER JOIN pessoa p "
                + "on a.idPessoa = p.id "
                + "INNER JOIN endereco e "
                + "on e.idPessoa = a.idPessoa WHERE p.id = ?";
        Aluno aluno = null;
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
                aluno = new Aluno();
                aluno.setId(id);
                aluno.setNome(resultSet.getString("nome"));
                aluno.setEmail(resultSet.getString("email"));
                aluno.setTelefones(telefones);
                endereco = new Endereco();
                endereco.setId(resultSet.getInt("e.id"));
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
                    telefone.setOperadora("operadora");
                    telefones.add(telefone);
                } while (resultSet.next());
                aluno.setTelefones(telefones);
            }
        }
        catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id. Erro =  " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return aluno;
    }

//    public List<Fornecedor> pesquisarPorNome(String nome) throws SQLException {
//        String consulta = "SELECT * FROM fornecedor f INNER JOIN pessoa p on f.idPessoa = p.id"
//                + " INNER JOIN endereco e on e.idFornecedor = f.idPessoa WHERE p.nome LIKE ?";
//        Fornecedor fornecedor = null;
//        Endereco endereco;
//        List<Fornecedor> fornecedores = new ArrayList<>();
//        List<Endereco> enderecos;
//        try {
//            conexao = FabricaConexao.abrirConexao();
//            preparando = conexao.prepareStatement(consulta);
//            preparando.setString(1, "%" + nome + "%");
//            resultSet = preparando.executeQuery();
//            int idFornecedor;
//            int idAntigo = 0;
//            while (resultSet.next()) {
//                idFornecedor = resultSet.getInt("p.id");
//
//                if (idFornecedor != idAntigo) {
//                    fornecedor = new Fornecedor();
//                    enderecos = new ArrayList<>();
//                    fornecedor.setEnderecos(enderecos);
//
//                    fornecedor.setId(idFornecedor);
//                    fornecedor.setNome(resultSet.getString("nome"));
//                    fornecedor.setEmail(resultSet.getString("email"));
//                    fornecedor.setTelefone(resultSet.getString("telefone"));
//                    fornecedor.setCnpj(resultSet.getString("cnpj"));
//                    fornecedor.setInscricaoEstadual(resultSet.getString("inscricaoEstadual"));
//                    idAntigo = idFornecedor;
//                    fornecedores.add(fornecedor);
//                }
//                endereco = new Endereco();
//                endereco.setId(resultSet.getInt("e.id"));
//                endereco.setLogradouro(resultSet.getString("logradouro"));
//                endereco.setNumero(resultSet.getString("numero"));
//                endereco.setBairro(resultSet.getString("bairro"));
//                endereco.setCidade(resultSet.getString("cidade"));
//                endereco.setEstado(resultSet.getString("estado"));
//                endereco.setCep(resultSet.getString("cep"));
//                fornecedor.getEnderecos().add(endereco);
//
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Erro ao pesquisar por id +" + e.getMessage());
//        } finally {
//            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
//        }
//        return fornecedores;
//    }

}
