package com.controlePedidos.domain.repository;

import com.controlePedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {

    Optional<Pedido> buscarPedidoPorId(UUID id);

    void salvarPedido(Pedido pedido);

    List<Pedido> listarPedidosAtivos();
}
