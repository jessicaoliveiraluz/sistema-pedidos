package com.controlePedidos.web.controller;

import com.controlePedidos.application.mapper.PedidoMapper;
import com.controlePedidos.application.service.PedidoService;
import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.web.dto.PedidoRequestDTO;
import com.controlePedidos.web.dto.PedidoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping("/efetuarPedido")
    public ResponseEntity<?> efetuarPedido(@RequestBody PedidoRequestDTO dto) {
        try {
            System.out.println("ENTROU");
            Pedido pedido = pedidoService.efetuarPedido(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toDTO(pedido));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("Erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Erro", "Erro inesperado ao efetuar o pedido."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable UUID id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> listarPedidosAtivos() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidosAtivos();
            List<PedidoResponseDTO> response = pedidos.stream()
                    .map(PedidoMapper::toDTO)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar pedidos ativos: " + e.getMessage());
        }
    }
}
