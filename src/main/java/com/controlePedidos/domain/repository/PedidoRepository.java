package com.controlePedidos.domain.repository;

import com.controlePedidos.domain.model.Pedido;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {
    //    void atualizar(Pedido pedido);
    Optional<Pedido> buscarPorId(UUID id);

    void salvar(Pedido pedido);
//    List<Pedido> listarTodos();
}
