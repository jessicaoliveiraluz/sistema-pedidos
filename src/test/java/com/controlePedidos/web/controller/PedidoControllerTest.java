package com.controlePedidos.web.controller;

import com.controlePedidos.application.mapper.PedidoMapper;
import com.controlePedidos.application.service.PedidoService;
import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.model.ProdutoSolicitado;
import com.controlePedidos.domain.model.StatusPedido;
import com.controlePedidos.web.dto.ItemPedidoDTO;
import com.controlePedidos.web.dto.PedidoRequestDTO;
import com.controlePedidos.web.dto.PedidoResponseDTO;
import com.controlePedidos.web.dto.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        pedidoService = mock(PedidoService.class);
        pedidoMapper = mock(PedidoMapper.class);
        pedidoController = new PedidoController(pedidoService, pedidoMapper);
    }

    @Test
    void deveEfetuarPedidoComSucesso() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setDescricao("Papel");
        produtoDTO.setQuantidade(2);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setProdutos(List.of(produtoDTO));

        ProdutoSolicitado produtoSolicitado = new ProdutoSolicitado("Papel", 2);
        produtoSolicitado.setPrecoUnitario(new BigDecimal("10.00"));

        Pedido pedido = new Pedido();
        UUID pedidoId = UUID.randomUUID();
        pedido.setId(pedidoId);
        pedido.setStatus(StatusPedido.ATIVO);
        pedido.setDataHora("2025-08-04T22:53:26.580399500");
        pedido.setProdutos(List.of(produtoSolicitado));
        pedido.setValorTotal(new BigDecimal("20.00"));

        when(pedidoService.efetuarPedido(dto)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.efetuarPedido(dto);

        PedidoResponseDTO esperado = PedidoMapper.toDTO(pedido);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(esperado, response.getBody());
    }

    @Test
    void deveRetornarBadRequestQuandoErroDeNegocioEmEfetuarPedido() {
        PedidoRequestDTO dto = new PedidoRequestDTO();

        when(pedidoService.efetuarPedido(dto))
                .thenThrow(new IllegalArgumentException("Produto inválido"));

        ResponseEntity<?> response = pedidoController.efetuarPedido(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Produto inválido"));
    }

    @Test
    void deveRetornarErro500QuandoErroInesperadoEmEfetuarPedido() {
        PedidoRequestDTO dto = new PedidoRequestDTO();

        when(pedidoService.efetuarPedido(dto)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = pedidoController.efetuarPedido(dto);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Erro inesperado"));
    }

    @Test
    void deveCancelarPedidoComSucesso() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> response = pedidoController.cancelarPedido(id);

        verify(pedidoService).cancelarPedido(id);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarBadRequestAoCancelarPedidoComEstadoInvalido() {
        UUID id = UUID.randomUUID();

        doThrow(new IllegalStateException("Pedido já cancelado")).when(pedidoService).cancelarPedido(id);

        ResponseEntity<?> response = pedidoController.cancelarPedido(id);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Pedido já cancelado", response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoCancelarPedidoInexistente() {
        UUID id = UUID.randomUUID();

        doThrow(new IllegalArgumentException("Pedido não encontrado")).when(pedidoService).cancelarPedido(id);

        ResponseEntity<?> response = pedidoController.cancelarPedido(id);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Pedido não encontrado", response.getBody());
    }

    @Test
    void deveListarPedidosAtivosComSucesso() {
        // Arrange
        Pedido pedido = new Pedido(
                UUID.randomUUID(),
                List.of(new ProdutoSolicitado("Papel", 1)),
                BigDecimal.valueOf(100),
                "2025-08-06T10:00:00",
                StatusPedido.ATIVO
        );

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());

        when(pedidoService.listarPedidosAtivos()).thenReturn(List.of(pedido));

        try (MockedStatic<PedidoMapper> mapperMocked = mockStatic(PedidoMapper.class)) {
            mapperMocked.when(() -> PedidoMapper.toDTO(pedido)).thenReturn(dto);

            ResponseEntity<?> response = pedidoController.listarPedidosAtivos();

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(List.of(dto), response.getBody());
        }
    }

    @Test
    void deveRetornarErro500AoListarPedidosAtivos() {
        when(pedidoService.listarPedidosAtivos()).thenThrow(new RuntimeException("Falha"));

        ResponseEntity<?> response = pedidoController.listarPedidosAtivos();

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Erro ao listar pedidos ativos"));
    }
}