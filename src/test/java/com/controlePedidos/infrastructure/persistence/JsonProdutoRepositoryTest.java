package com.controlePedidos.infrastructure.persistence;

import com.controlePedidos.domain.model.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JsonProdutoRepositoryTest {

    private ObjectMapper mapper;
    private JsonProdutoRepository repository;
    private Produto produto;

    @BeforeEach
    void setup() {
        mapper = mock(ObjectMapper.class);
        repository = new JsonProdutoRepository(mapper);
        produto = new Produto("1", "Arroz", 10, new BigDecimal("5.00"));
    }

    @Test
    void deveSalvarNovoProdutoComSucesso() throws Exception {
        List<Produto> produtosNoArquivo = new ArrayList<>();
        when(mapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(produtosNoArquivo);

        repository.salvarProduto(produto);

        verify(mapper).writeValue(any(File.class), eq(List.of(produto)));
    }

    @Test
    void deveAtualizarProdutoExistente() throws Exception {
        Produto produtoAntigo = new Produto("1", "Arroz", 3, new BigDecimal("4.00"));
        List<Produto> produtosNoArquivo = new ArrayList<>();
        produtosNoArquivo.add(produtoAntigo);

        when(mapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(produtosNoArquivo);

        repository.salvarProduto(produto);

        assertEquals(1, produtosNoArquivo.size());
        verify(mapper).writeValue(any(File.class), eq(produtosNoArquivo));
    }

    @Test
    void deveBuscarProdutoPorDescricaoComSucesso() throws Exception {
        Produto outroProduto = new Produto(null, "Feijão", 5, new BigDecimal("3.50"));
        List<Produto> produtos = List.of(produto, outroProduto);

        when(mapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(produtos);

        Optional<Produto> encontrado = repository.buscarProdutoPorDescricao("arroz");

        assertTrue(encontrado.isPresent());
        assertEquals("Arroz", encontrado.get().getDescricao());
    }

    @Test
    void deveRetornarVazioQuandoProdutoNaoEncontrado() throws Exception {
        List<Produto> produtos = List.of(new Produto("1", "Feijão", 5, new BigDecimal("3.50")));

        when(mapper.readValue(any(File.class), any(TypeReference.class)))
                .thenReturn(produtos);

        Optional<Produto> encontrado = repository.buscarProdutoPorDescricao("arroz");

        assertTrue(encontrado.isEmpty());
    }
}