/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.controle;

import br.com.associacao.entidade.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ClienteControle implements Serializable {

    private List<Cliente> clientes = new ArrayList<>();

    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    public Integer pegarUltimoId() {
        int tamanhoLista = clientes.size();
        if(tamanhoLista == 0){
            return 0;
        }
        return clientes.get(tamanhoLista - 1).getId();
    }
    
    public List<Cliente> pesquisarPorNome(String nome){
        List<Cliente> clientesPesquisado = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if(cliente.getNome().contains(nome)){
                clientesPesquisado.add(cliente);
            }
        }
        return clientesPesquisado;
    }

    
}
