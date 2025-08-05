package com.controlePedidos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private UUID id;
    private List<ItemPedidoDTO> itens;
    private BigDecimal valorTotal;
    private String dataHora;
    private String status;
}
