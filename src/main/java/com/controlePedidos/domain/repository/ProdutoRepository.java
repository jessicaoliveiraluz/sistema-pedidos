package com.controlePedidos.domain.repository;

import com.controlePedidos.domain.model.Produto;

import java.util.Optional;

public interface ProdutoRepository {
    //    Optional<Produto> buscarPorId(String id);
    Optional<Produto> buscarPorDescricao(String descricao); // ADICIONE esta para o método atual

    void salvar(Produto produto); // renomeado de "atualizar", pois pode ser inclusão ou update
//    List<Produto> listarTodos();
}
