package com.controlePedidos.application.mapper;

import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.model.ProdutoSolicitado;
import com.controlePedidos.web.dto.ItemPedidoDTO;
import com.controlePedidos.web.dto.PedidoResponseDTO;
import com.controlePedidos.web.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    // Transforma entidade em DTO
    public static PedidoResponseDTO toDTO(Pedido pedido) {
        List<ItemPedidoDTO> itens = pedido.getProdutos().stream().map(produto -> {
            ItemPedidoDTO item = new ItemPedidoDTO();
            item.setDescricao(produto.getDescricao());
            item.setQuantidade(produto.getQuantidade());
            item.setPrecoUnitario(produto.getPrecoUnitario());
            return item;
        }).collect(Collectors.toList());

        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus().name());
        dto.setDataHora(pedido.getDataHora().toString());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setItens(itens);
        return dto;
    }

    // Transforma DTO de entrada em entidade de domínio
    public static List<ProdutoSolicitado> toProdutos(List<ProdutoDTO> dtos) {
        return dtos.stream().map(dto -> new ProdutoSolicitado(dto.getDescricao(), dto.getQuantidade()))
                .collect(Collectors.toList());
    }
}
