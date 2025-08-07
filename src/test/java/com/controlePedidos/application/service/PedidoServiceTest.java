package com.controlePedidos.application.service;

import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.model.Produto;
import com.controlePedidos.domain.model.StatusPedido;
import com.controlePedidos.domain.repository.PedidoRepository;
import com.controlePedidos.domain.repository.ProdutoRepository;
import com.controlePedidos.web.dto.PedidoRequestDTO;
import com.controlePedidos.web.dto.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Produto produtoMock;

    @BeforeEach
    void setUp() {
        produtoMock = new Produto();
        produtoMock.setId("1");
        produtoMock.setDescricao("Papel");
        produtoMock.setQuantidadeEstoque(100);
        produtoMock.setPrecoUnitario(new BigDecimal("10.00"));
    }

    @Test
    void deveEfetuarPedidoComSucesso() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Papel", 2);
        PedidoRequestDTO pedidoRequest = new PedidoRequestDTO(List.of(produtoDTO));

        when(produtoRepository.buscarProdutoPorDescricao("Papel"))
                .thenReturn(Optional.of(produtoMock));

        Pedido pedido = pedidoService.efetuarPedido(pedidoRequest);

        assertNotNull(pedido.getId());
        assertEquals(1, pedido.getProdutos().size());
        assertEquals("Papel", pedido.getProdutos().get(0).getDescricao());
        assertEquals(new BigDecimal("20.00"), pedido.getValorTotal());
        assertEquals(StatusPedido.ATIVO, pedido.getStatus());

        verify(produtoRepository, times(1)).salvarProduto(any());
        verify(pedidoRepository, times(1)).salvarPedido(any());
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoExistir() {
        ProdutoDTO produtoDTO = new ProdutoDTO("Caneta", 1);
        PedidoRequestDTO pedidoRequest = new PedidoRequestDTO(List.of(produtoDTO));

        when(produtoRepository.buscarProdutoPorDescricao("Caneta"))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.efetuarPedido(pedidoRequest);
        });

        assertEquals("Produto não encontrado: Caneta", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeEstoqueInsuficiente() {
        Produto produtoComPoucoEstoque = new Produto("2", "Leite", 1, new BigDecimal("5.00"));
        ProdutoDTO produtoDTO = new ProdutoDTO("Leite", 5);
        PedidoRequestDTO pedidoRequest = new PedidoRequestDTO(List.of(produtoDTO));

        when(produtoRepository.buscarProdutoPorDescricao("Leite"))
                .thenReturn(Optional.of(produtoComPoucoEstoque));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.efetuarPedido(pedidoRequest);
        });

        assertEquals("Estoque insuficiente para: Leite", exception.getMessage());
    }
}
