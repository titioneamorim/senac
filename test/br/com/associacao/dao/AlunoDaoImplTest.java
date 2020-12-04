/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Aluno;
import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Telefone;
import br.com.utilitario.UtilGerador;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Titione
 */
public class AlunoDaoImplTest {
    
    private Aluno aluno;
    private AlunoDaoImpl alunoDaoImpl;
    
    public AlunoDaoImplTest() {
        alunoDaoImpl = new AlunoDaoImpl();
    }

    @Test
    public void testSalvar() throws Exception {
        System.out.println("Teste de criação de aluno: ");
        
        List<Telefone> telefones = new ArrayList<>();
        
        aluno = new Aluno(
                UtilGerador.gerarCaracter(8), 
                null, 
                UtilGerador.gerarNome(), 
                UtilGerador.gerarEmail(), 
                UtilGerador.gerarNumero(11), //cpf
                UtilGerador.gerarNumero(8), //rg 
                telefones, 
                gerarEndereco(aluno.getId())
        );
                 
                
                
                
        for (int i = 0; i < 3; i++) {
            telefones.add(gerarTelefone());
        }
        aluno.setTelefones(telefones);
        alunoDaoImpl.salvar(aluno);
    }

   
    private Telefone gerarTelefone() {
        Telefone telefone = new Telefone(
                Integer.parseInt(UtilGerador.gerarNumero(3)), 
                UtilGerador.gerarCaracter(4), 
                UtilGerador.gerarTelefoneFixo(), 
                UtilGerador.gerarCaracter(0));
        
        return telefone;
    }
    
    private Endereco gerarEndereco(Integer idPessoa){
        Endereco endereco = new Endereco(
               Integer.parseInt(UtilGerador.gerarNumero(3)), 
               UtilGerador.gerarCaracter(7), 
               UtilGerador.gerarCaracter(4), 
               UtilGerador.gerarNome(), 
               UtilGerador.gerarCidade(),
               UtilGerador.gerarNome(), 
               "Casa", 
                idPessoa);
        return endereco;
    }
    
}
