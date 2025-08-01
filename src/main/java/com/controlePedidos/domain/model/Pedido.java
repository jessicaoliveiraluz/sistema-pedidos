package com.controlePedidos.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Pedido {

    private UUID id;
    private String descricao;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private StatusPedido status;
}
