/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.view;

import br.com.associacao.controle.ClienteControle;
import br.com.associacao.entidade.Cliente;
import br.com.associacao.entidade.Endereco;
import br.com.utilitario.UtilGerador;
import java.awt.SystemColor;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class TestaCliente {

    private static ClienteControle controle = new ClienteControle();

    public static void main(String[] args) {

        String menu;
        do {
            menu = JOptionPane.showInputDialog("Escolha a opção:\n(1) Salvar\n(2) Pesquisar\n(5) Sair");

            switch (menu) {
                case "1":
                    Cliente cliente = criarCliente();
                    controle.salvar(cliente);
                    mostrar(cliente);
                    break;

                case "2":
                    String nome = JOptionPane.showInputDialog("Digite o nome a ser pesquisado");
                    List<Cliente> clientes = controle.pesquisarPorNome(nome);
                    System.out.println("");
                    System.out.println("Resultado");
                    System.out.println("");
                    if (clientes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado ninguém com esse valor!");
                    } else {
                        for (Cliente cliente1 : clientes) {
                            mostrar(cliente1);
                            System.out.println("");
                        }
                    }
                    break;

                case "5":
                    break;
            }
        } while (true);
    }

    private static Cliente criarCliente() {
        Integer id = controle.pegarUltimoId();
        Cliente cliente = new Cliente(++id, UtilGerador.gerarNome(), UtilGerador.gerarEmail(),
                UtilGerador.gerarTelefoneFixo(), Double.parseDouble(UtilGerador.gerarNumero(4)));
//        Endereco endereco = new Endereco(++id, "Rua " + UtilGerador.gerarCaracter(7), UtilGerador.gerarNumero(4), "Bairro " + UtilGerador.gerarCaracter(7), UtilGerador.gerarCidade(), "Santa Catarina");
//        cliente.setEndereco(endereco);
        return cliente;
    }

    private static void mostrar(Cliente cliente) {
        System.out.println("Id: " + cliente.getId());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("E-Mail: " + cliente.getEmail());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Salário: " + cliente.getSalario());
        System.out.println("Id Endereço: " + cliente.getEndereco().getId());
        System.out.println("Logradouro: " + cliente.getEndereco().getLogradouro());
        System.out.println("Número: " + cliente.getEndereco().getNumero());
        System.out.println("Bairro: " + cliente.getEndereco().getBairro());
        System.out.println("Cidade: " + cliente.getEndereco().getCidade());
        System.out.println("Estado: " + cliente.getEndereco().getEstado());
        System.out.println("");
    }
}
