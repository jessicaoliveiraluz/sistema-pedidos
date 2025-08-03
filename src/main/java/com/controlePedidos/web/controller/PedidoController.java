package com.controlePedidos.web.controller;

import com.controlePedidos.application.service.PedidoService;
import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.web.dto.PedidoRequestDTO;
import com.controlePedidos.web.dto.PedidoResponseDTO;
import com.controlePedidos.application.mapper.PedidoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(PedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> efetuarPedido(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = pedidoService.efetuarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoMapper.toDTO(pedido));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> cancelarPedido(@PathVariable String id) {
//        pedidoService.cancelarPedido(id);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping
//    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
//        List<Pedido> pedidos = pedidoService.listarPedidosAtivos();
//        List<PedidoResponseDTO> response = pedidos.stream()
//                .map(PedidoResponseDTO::from)
//                .toList();
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping
//    public ResponseEntity<List<PedidoRequestDTO>> listarPedidos() {
//        List<PedidoRequestDTO> pedidos = pedidoService.listarPedidos()
//                .stream()
//                .map(PedidoRequestDTO::from)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(pedidos);
//    }
}
