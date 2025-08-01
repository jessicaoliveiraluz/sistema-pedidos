package com.controlePedidos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Produto {

    private UUID id;
    private String nome;
    private Integer estoque;
    private BigDecimal precoUnitario;
}
