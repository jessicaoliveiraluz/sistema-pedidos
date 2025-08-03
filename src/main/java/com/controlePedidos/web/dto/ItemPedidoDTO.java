package com.controlePedidos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private String descricao;
    private int quantidade;
    private BigDecimal precoUnitario;
}
