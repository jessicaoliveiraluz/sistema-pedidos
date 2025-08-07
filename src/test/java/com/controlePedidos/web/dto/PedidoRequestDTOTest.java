package com.controlePedidos.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PedidoRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarPedidoComProdutoInvalido() {
        ProdutoDTO produtoInvalido = new ProdutoDTO("", 0); // inválido
        PedidoRequestDTO pedido = new PedidoRequestDTO(List.of(produtoInvalido));

        Set<ConstraintViolation<PedidoRequestDTO>> violations = validator.validate(pedido);

        assertFalse(violations.isEmpty());
        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }
}