package com.controlePedidos.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {

    @Test
    void deveRemoverQuantidadeValidaDoEstoque() {
        Produto produto = new Produto("1", "Camiseta", 10, BigDecimal.valueOf(50));

        produto.removerDoEstoque(3);

        assertEquals(7, produto.getQuantidadeEstoque());
    }

    @Test
    void deveLancarExcecaoQuandoRemoverQuantidadeMenorOuIgualAZero() {
        Produto produto = new Produto("1", "Camiseta", 10, BigDecimal.valueOf(50));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> produto.removerDoEstoque(0)
        );

        assertEquals("A quantidade a remover deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoRemoverMaisQueEstoqueDisponivel() {
        Produto produto = new Produto("1", "Camiseta", 5, BigDecimal.valueOf(50));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> produto.removerDoEstoque(10)
        );

        assertEquals("Estoque insuficiente para o produto: Camiseta", ex.getMessage());
    }

    @Test
    void deveAdicionarQuantidadeValidaAoEstoque() {
        Produto produto = new Produto("1", "Camiseta", 10, BigDecimal.valueOf(50));

        produto.adicionarAoEstoque(5);

        assertEquals(15, produto.getQuantidadeEstoque());
    }

    @Test
    void deveLancarExcecaoQuandoAdicionarQuantidadeMenorOuIgualAZero() {
        Produto produto = new Produto("1", "Camiseta", 10, BigDecimal.valueOf(50));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> produto.adicionarAoEstoque(0)
        );

        assertEquals("A quantidade a adicionar deve ser maior que zero.", ex.getMessage());
    }
}
