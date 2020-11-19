/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class FabricaConexao {
    
    public static Connection abrirConexao()throws SQLException{
        Connection driver = null;
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           driver = DriverManager.getConnection("jdbc:mysql://localhost:3306/associacao"
               + "?useTimezone=true&serverTimezone=America/Sao_Paulo&zeroDateTimeBehavior=convertToNull",
                    "root", "123Senac");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco " + e.getMessage());
        }
        return driver;
    }
    
    
    public static void fecharConexao(Connection conn, Statement psmt, ResultSet rs) throws SQLException {
        rs.close();
        conn.close();
        psmt.close();
    }

    public static void fecharConexao(Connection conn, Statement psmt) throws SQLException {
        conn.close();
        psmt.close();
    }   
    
}
