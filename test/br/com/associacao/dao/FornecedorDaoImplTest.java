/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.associacao.dao;

import br.com.associacao.entidade.Endereco;
import br.com.associacao.entidade.Fornecedor;
import br.com.utilitario.UtilGerador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class FornecedorDaoImplTest {

    private Fornecedor fornecedor;
    private FornecedorDaoImpl fornecedorDaoImpl;

    public FornecedorDaoImplTest() {
        fornecedorDaoImpl = new FornecedorDaoImpl();
    }

    public void testSalvar() throws Exception {
        System.out.println("salvar");

        List<Endereco> enderecos = new ArrayList<>();
        fornecedor = new Fornecedor(null, UtilGerador.gerarNumero(8), UtilGerador.gerarNumero(9),
                UtilGerador.gerarNome(), UtilGerador.gerarEmail(), UtilGerador.gerarTelefoneFixo());

        for (int i = 0; i < 2; i++) {
            enderecos.add(gerarEndereco());
        }
        
        fornecedor.setEnderecos(enderecos);
        fornecedorDaoImpl.salvar(fornecedor);

    }

    private Endereco gerarEndereco() {
        Endereco endereco = new Endereco(null, "Rua " + UtilGerador.gerarCaracter(10), UtilGerador.gerarNumero(3),
                "Bairro " + UtilGerador.gerarCaracter(10), "Cidade " + UtilGerador.gerarCaracter(10), "Estado " + UtilGerador.gerarCaracter(2),
                "CEP " + UtilGerador.gerarNumero(5) + "-" + UtilGerador.gerarNumero(3));
        return endereco;
    }

}
