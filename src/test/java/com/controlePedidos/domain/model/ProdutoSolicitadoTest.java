package com.controlePedidos.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoSolicitadoTest {

    @Test
    void deveCriarProdutoSolicitadoComTodosOsCampos() {
        ProdutoSolicitado produto = new ProdutoSolicitado("Camiseta", 2, BigDecimal.valueOf(49.99));

        assertEquals("Camiseta", produto.getDescricao());
        assertEquals(2, produto.getQuantidade());
        assertEquals(BigDecimal.valueOf(49.99), produto.getPrecoUnitario());
    }

    @Test
    void deveCriarProdutoSolicitadoComConstrutorParcial() {
        ProdutoSolicitado produto = new ProdutoSolicitado("Shorts", 3);

        assertEquals("Shorts", produto.getDescricao());
        assertEquals(3, produto.getQuantidade());
        assertNull(produto.getPrecoUnitario());
    }

    @Test
    void devePermitirModificarCamposComSetters() {
        ProdutoSolicitado produto = new ProdutoSolicitado();

        produto.setDescricao("Tênis");
        produto.setQuantidade(1);
        produto.setPrecoUnitario(BigDecimal.valueOf(199.90));

        assertEquals("Tênis", produto.getDescricao());
        assertEquals(1, produto.getQuantidade());
        assertEquals(BigDecimal.valueOf(199.90), produto.getPrecoUnitario());
    }
}