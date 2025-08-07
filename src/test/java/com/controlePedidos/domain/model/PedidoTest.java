package com.controlePedidos.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    void deveCancelarPedidoComSucesso() {
        Pedido pedido = new Pedido(
                UUID.randomUUID(),
                List.of(new ProdutoSolicitado("Produto A", 2)),
                BigDecimal.valueOf(100),
                "2025-08-04T10:00:00",
                StatusPedido.ATIVO
        );
        pedido.cancelar();

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    @Test
    void deveLancarExcecaoAoCancelarPedidoJaCancelado() {
        Pedido pedido = new Pedido(
                UUID.randomUUID(),
                List.of(new ProdutoSolicitado("Produto B", 1)),
                BigDecimal.valueOf(50),
                "2025-08-04T10:00:00",
                StatusPedido.CANCELADO
        );

        IllegalStateException exception = assertThrows(IllegalStateException.class, pedido::cancelar);
        assertEquals("Pedido já está cancelado.", exception.getMessage());
    }
}
