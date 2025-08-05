package com.controlePedidos.domain.repository;

import com.controlePedidos.domain.model.Produto;

import java.util.Optional;

public interface ProdutoRepository {

    Optional<Produto> buscarProdutoPorDescricao(String descricao); // ADICIONE esta para o método atual

    void salvarProduto(Produto produto); // renomeado de "atualizar", pois pode ser inclusão ou update
}
