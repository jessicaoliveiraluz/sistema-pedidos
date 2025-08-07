package com.controlePedidos.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProdutoDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveRetornarErroQuandoDescricaoForVazia() {
        ProdutoDTO dto = new ProdutoDTO("", 5);

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Descrição do produto não pode ser vazia", violations.iterator().next().getMessage());
    }

    @Test
    void deveRetornarErroQuandoQuantidadeForMenorQue1() {
        ProdutoDTO dto = new ProdutoDTO("Arroz", 0);

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("A quantidade deve ser no mínimo 1", violations.iterator().next().getMessage());
    }

    @Test
    void devePassarQuandoDadosSaoValidos() {
        ProdutoDTO dto = new ProdutoDTO("Arroz", 2);

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(dto);

        assertEquals(0, violations.size());
    }
}