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
        String sql = "INSERT INTO aluno(matricula, idPessoa) VALUES(?, ?)";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, aluno.getMatricula());
            preparando.setInt(2, aluno.getId());
            preparando.executeUpdate();
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            enderecoDaoImpl.salvarEnderecoAluno(aluno.getEndereco(), aluno.getId(), conexao);
            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();
            telefoneDaoImpl.salvarTelefone(aluno.getTelefones(), aluno.getId(), conexao);

        } catch (SQLException eSQL) {
            System.out.println("Erro ao salvar aluno: Erro = " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public void alterar(Aluno aluno) throws SQLException {
        super.alterar(aluno);
        String sql = "UPDATE pessoa SET cpf = ?, nome = ? , rg = ?, email = ? WHERE id = ?";
        try {
            preparando = conexao.prepareStatement(sql);
            preparando.setString(1, aluno.getCpf());
            preparando.setString(2, aluno.getNome());
            preparando.setString(3, aluno.getRg());
            preparando.setString(4, aluno.getEmail());
            preparando.setInt(5, aluno.getId());
            preparando.executeUpdate();

            TelefoneDaoImpl telefoneDaoImpl = new TelefoneDaoImpl();

            for (Telefone telefone : aluno.getTelefones()) {
                telefoneDaoImpl.alterar(telefone, conexao);
            }

        } catch (SQLException eSQL) {
            System.err.println("Erro ao alterar aluno " + eSQL.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }

    public Aluno pesquisarPorId(Integer id) throws SQLException {
        String consulta = "SELECT * , e.id as idEndereco FROM aluno a "
                + "INNER JOIN pessoa p on a.idPessoa = p.id "
                + "INNER JOIN endereco e on e.idPessoa = a.idPessoa "
                + "INNER JOIN telefone t on a.idPessoa = t.idPessoa WHERE p.id = ?";
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
                aluno.setCpf(resultSet.getString("cpf"));
                aluno.setRg(resultSet.getString("rg"));
                aluno.setMatricula(resultSet.getString("matricula"));
                aluno.setTelefones(telefones);
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
                    telefone.setOperadora("operadora");
                    telefones.add(telefone);
                } while (resultSet.next());
                aluno.setTelefones(telefones);
                aluno.setEndereco(endereco);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por id. Erro =  " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return aluno;
    }

    public List<Aluno> pesquisarPorNome(String nome) throws SQLException {
        String consulta = "SELECT * , e.id as idEndereco FROM aluno a "
                + "INNER JOIN pessoa p on a.idPessoa = p.id "
                + "INNER JOIN endereco e on e.idPessoa = a.idPessoa "
                + "INNER JOIN telefone t on a.idPessoa = t.idPessoa WHERE p.nome LIKE ?";
        Aluno aluno = null;
        Endereco endereco;
        Telefone telefone;
        List<Aluno> alunos = new ArrayList<>();
        List<Telefone> telefones;

        try {
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(consulta);
            preparando.setString(1, "%" + nome + "%");
            resultSet = preparando.executeQuery();

            int idAluno;
            int idAntigo = 0;
            while (resultSet.next()) {
                idAluno = resultSet.getInt("idPessoa");

                if (idAluno != idAntigo) {
                    aluno = new Aluno();
                    telefones = new ArrayList<>();
                    aluno.setTelefones(telefones);
                    aluno.setId(resultSet.getInt("id"));
                    aluno.setNome(resultSet.getString("nome"));
                    aluno.setEmail(resultSet.getString("email"));
                    aluno.setCpf(resultSet.getString("cpf"));
                    aluno.setRg(resultSet.getString("rg"));
                    aluno.setMatricula(resultSet.getString("matricula"));
                    endereco = new Endereco();
                    endereco.setId(resultSet.getInt("idEndereco"));
                    endereco.setLogradouro(resultSet.getString("logradouro"));
                    endereco.setNumero(resultSet.getString("numero"));
                    endereco.setBairro(resultSet.getString("bairro"));
                    endereco.setCidade(resultSet.getString("cidade"));
                    endereco.setEstado(resultSet.getString("estado"));
                    idAntigo = idAluno;
                    aluno.setEndereco(endereco);
                    alunos.add(aluno);
                }
                telefone = new Telefone();
                telefone.setId(resultSet.getInt("t.id"));
                telefone.setNumero(resultSet.getString("numero"));
                telefone.setTipo("tipo");
                telefone.setOperadora("operadora");
                aluno.getTelefones().add(telefone);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar por nome = " + e.getMessage());
        } finally {
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
        return alunos;
    }

}
