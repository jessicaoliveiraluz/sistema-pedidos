package com.controlePedidos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    private UUID id;
    private List<ProdutoSolicitado> produtos;
    private BigDecimal valorTotal;
    private String dataHora;
    private StatusPedido status;

    public void cancelar() {
        if (this.status == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido já está cancelado.");
        }
        this.status = StatusPedido.CANCELADO;
    }
}
