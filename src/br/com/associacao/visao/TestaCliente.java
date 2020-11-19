/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.visao;

import br.com.associacao.controle.ClienteControle;
import br.com.associacao.entidade.Cliente;
import br.com.associacao.entidade.Endereco;
import br.com.utilitario.UtilGerador;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class TestaCliente {

//    private static Cliente cliente;
    private static ClienteControle controle = new ClienteControle();
    
    
    public static void main(String[] args) {
        String menu;
        do {
            menu = JOptionPane.showInputDialog("Escolha a opção:\n(1)Salvar \n(2)Pesquisar Por nome"
                    + "\n(3)Pesquisar Por nome  \n(5)Sair");

            switch (menu) {
                case "1":
                    Cliente cliente = criarCliente();
                    controle.salvar(cliente);
                    mostrar(cliente);
                    System.out.println("");
                    break;
                case "2":
                    String nome = JOptionPane.showInputDialog("Digite o nome do cliente");
                    List<Cliente> clientes = controle.pesquisarPorNome(nome);
                    if(clientes.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Não foi encontrado ninguém com esse valor!");
                    }else{
                        for (Cliente cliente1 : clientes) {
                            mostrar(cliente1);
                            System.out.println("");
                        }
                    }
                    break;
                    
                case "5":
                    break;
            }

        } while (!menu.equals("5"));
    }
    
    private static Cliente criarCliente(){
        Integer id = controle.pegarUltimoId() + 1;
        Cliente cliente = new Cliente(id, UtilGerador.gerarNome(), UtilGerador.gerarEmail(),
                 UtilGerador.gerarTelefoneFixo(), Double.parseDouble(UtilGerador.gerarNumero(4)));
//        Endereco endereco = new Endereco(id, "Rua " + UtilGerador.gerarCaracter(7),
//                UtilGerador.gerarNumero(3), "Bairro " + UtilGerador.gerarCaracter(7),
//                UtilGerador.gerarCidade(), "Santa Catarina ");
//        cliente.setEndereco(endereco);
        return cliente;
    }
    
    private static void mostrar(Cliente cliente){
        System.out.println("Id: " + cliente.getId() );
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("E-mail: " + cliente.getEmail());
        System.out.println("E-Tel: " + cliente.getTelefone());
        System.out.println("Salário: " + cliente.getSalario());
        System.out.println("ID Endereço: " + cliente.getEndereco().getId());
        System.out.println("Logradouro: " + cliente.getEndereco().getLogradouro());
        System.out.println("Número: " + cliente.getEndereco().getNumero());
        System.out.println("Bairro: " + cliente.getEndereco().getBairro());
        System.out.println("Cidade: " + cliente.getEndereco().getCidade());
        System.out.println("Estado: " + cliente.getEndereco().getEstado());
    }
    
    
    
}
