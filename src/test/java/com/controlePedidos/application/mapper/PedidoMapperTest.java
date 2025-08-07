package com.controlePedidos.application.mapper;

import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.model.ProdutoSolicitado;
import com.controlePedidos.domain.model.StatusPedido;
import com.controlePedidos.web.dto.ItemPedidoDTO;
import com.controlePedidos.web.dto.PedidoResponseDTO;
import com.controlePedidos.web.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PedidoMapperTest {

    @Test
    void deveConverterPedidoParaPedidoResponseDTO() {
        ProdutoSolicitado produto1 = new ProdutoSolicitado("Mouse", 2, BigDecimal.valueOf(50.0));
        ProdutoSolicitado produto2 = new ProdutoSolicitado("Teclado", 1, BigDecimal.valueOf(150.0));
        List<ProdutoSolicitado> produtos = Arrays.asList(produto1, produto2);

        String uuid = "8f3ec864-dcbb-49ba-a572-8fa522c40886";
        Pedido pedido = new Pedido();
        pedido.setId(UUID.fromString(uuid));
        pedido.setProdutos(produtos);
        pedido.setStatus(StatusPedido.ATIVO);
        pedido.setDataHora("2025-08-04T22:53:26.580399500");
        pedido.setValorTotal(BigDecimal.valueOf(250.0));

        PedidoResponseDTO dto = PedidoMapper.toDTO(pedido);

        assertEquals(UUID.fromString(uuid), dto.getId());
        assertEquals("ATIVO", dto.getStatus());
        assertEquals("2025-08-04T22:53:26.580399500", dto.getDataHora());
        assertEquals(BigDecimal.valueOf(250.0), dto.getValorTotal());

        assertEquals(2, dto.getItens().size());

        ItemPedidoDTO item1 = dto.getItens().get(0);
        assertEquals("Mouse", item1.getDescricao());
        assertEquals(2, item1.getQuantidade());
        assertEquals(BigDecimal.valueOf(50.0), item1.getPrecoUnitario());
    }

    @Test
    void deveConverterProdutoDTOParaProdutoSolicitado() {
        ProdutoDTO dto1 = new ProdutoDTO();
        dto1.setDescricao("Notebook");
        dto1.setQuantidade(1);

        ProdutoDTO dto2 = new ProdutoDTO();
        dto2.setDescricao("Monitor");
        dto2.setQuantidade(2);

        List<ProdutoDTO> dtos = Arrays.asList(dto1, dto2);

        List<ProdutoSolicitado> produtos = PedidoMapper.toProdutos(dtos);

        assertEquals(2, produtos.size());

        ProdutoSolicitado produto1 = produtos.get(0);
        assertEquals("Notebook", produto1.getDescricao());
        assertEquals(1, produto1.getQuantidade());
        assertNull(produto1.getPrecoUnitario()); // pois não vem do DTO

        ProdutoSolicitado produto2 = produtos.get(1);
        assertEquals("Monitor", produto2.getDescricao());
        assertEquals(2, produto2.getQuantidade());
    }
}

