package com.controlePedidos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoSolicitado {

    private String descricao;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ProdutoSolicitado(String descricao, int quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
    }
}
