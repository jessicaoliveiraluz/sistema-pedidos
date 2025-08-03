package com.controlePedidos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private String id;
    private List<ItemPedidoDTO> itens;
    private BigDecimal valorTotal;
    private LocalDateTime dataHora;
    private String status;
}
