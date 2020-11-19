/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Fornecedor;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class FornecedorDaoImpl implements Serializable{
    
    private Connection conexao;
    private PreparedStatement preparando;
    private ResultSet resultSet;
    
    public void salvar(Fornecedor fornecedor) throws SQLException {
        String sql = "INSERT INTO fornecedor(nome, cnpj, inscricaoEstadual, email, telefone) VALUES(?, ?, ?, ?, ?)";
        
        try {
            
            conexao = FabricaConexao.abrirConexao();
            preparando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparando.setString(1, fornecedor.getNome());
            preparando.setString(2, fornecedor.getCnpj());
            preparando.setString(3, fornecedor.getInscricaoEstadual());
            preparando.setString(4, fornecedor.getEmail());
            preparando.setString(5, fornecedor.getTelefone());
            preparando.executeUpdate();
            
            resultSet = preparando.getGeneratedKeys(); //utilizar quando usar outra tabela para pegar a chave primaria
            resultSet.next(); //acessar resultset para verificar se tem registro
            fornecedor.setId(resultSet.getInt(1)); //retorna o primeiro valor de resultset
            
            EnderecoDaoImpl enderecoDaoImpl = new EnderecoDaoImpl();
            
            for (Endereco endereco : fornecedor.getEnderecos()) {
                enderecoDaoImpl.salvarEnderecoFornecedor(endereco, fornecedor.getId(), conexao);
            }
        } catch (SQLException eSQL) {
            System.out.println("Erro ao salvar fornecedor " + eSQL.getMessage());
        }finally{
            FabricaConexao.fecharConexao(conexao, preparando, resultSet);
        }
    }
    
}
