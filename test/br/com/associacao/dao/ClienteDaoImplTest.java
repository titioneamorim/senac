/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Cliente;
import br.com.associacao.entidade.Endereco;
import br.com.utilitario.UtilGerador;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author HP
 */
public class ClienteDaoImplTest {

    private Cliente cliente;
    private ClienteDaoImpl clienteDaoImpl;

    public ClienteDaoImplTest() {
        clienteDaoImpl = new ClienteDaoImpl();
    }

    //@Test
    public void testSalvarCliente() throws Exception {
        System.out.println("salvar");
        cliente = new Cliente(null, UtilGerador.gerarCaracter(10), UtilGerador.gerarEmail(),
                UtilGerador.gerarTelefoneFixo(), Double.parseDouble(UtilGerador.gerarNumero(3)));

        Endereco endereco = new Endereco(null, UtilGerador.gerarCaracter(10), UtilGerador.gerarNumero(3),
                UtilGerador.gerarCaracter(10), UtilGerador.gerarCaracter(10), UtilGerador.gerarCaracter(2),
                UtilGerador.gerarNumero(5) + "-" + UtilGerador.gerarNumero(3));
        
        cliente.setEndereco(endereco);
        
        clienteDaoImpl.salvar(cliente);
    }
    
    @org.junit.Test
    public void testAlterar() throws Exception {
        System.out.println("alterar");
        
        cliente = clienteDaoImpl.pesquisarPorId(11);
        cliente.setNome("aaa" + UtilGerador.gerarNome());
        cliente.getEndereco().setLogradouro("log alterado");
        
        clienteDaoImpl.alterar(cliente);
    }

    // @Test
    public void testExcluir() throws Exception {
        System.out.println("excluir");
        clienteDaoImpl.excluir(3);
    }

    //@Test
    public void testPesquisarPorId() throws Exception {
        System.out.println("pesquisar Por Id");
        cliente = clienteDaoImpl.pesquisarPorId(11);
        System.out.println("id: " + cliente.getId());
        System.out.println("nome: " + cliente.getNome());
        System.out.println("email: " + cliente.getEmail());
        System.out.println("telefone: " + cliente.getTelefone());
        System.out.println("salario: R$" + cliente.getSalario());
        System.out.println("Id Endereço: " + cliente.getEndereco().getId());
        System.out.println("Logradouro: " + cliente.getEndereco().getLogradouro());
        System.out.println("Número: " + cliente.getEndereco().getNumero());
        System.out.println("Bairro: " + cliente.getEndereco().getBairro());
        System.out.println("Estado: " + cliente.getEndereco().getEstado());
        System.out.println("CEP: " + cliente.getEndereco().getCep());
            }

    //@Test
    public void testPesquisarPorNome() throws Exception {
        System.out.println("pesquisar Por Nome");
        List<Cliente> clientes = clienteDaoImpl.pesquisarPorNome("D");
        for (Cliente cli : clientes) {
            System.out.println("id: " + cli.getId());
            System.out.println("nome: " + cli.getNome());
            System.out.println("email: " + cli.getEmail());
            System.out.println("telefone: " + cli.getTelefone());
            System.out.println("salario: R$" + cli.getSalario());
            System.out.println("");
        }
    }
}
