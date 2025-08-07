package com.controlePedidos.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotBlank(message = "Descrição do produto não pode ser vazia")
    private String descricao;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private int quantidade;
}
