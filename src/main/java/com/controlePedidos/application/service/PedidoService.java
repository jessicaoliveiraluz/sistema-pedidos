package com.controlePedidos.application.service;

import com.controlePedidos.domain.model.*;
import com.controlePedidos.domain.repository.PedidoRepository;
import com.controlePedidos.domain.repository.ProdutoRepository;

import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.model.Produto;
import com.controlePedidos.web.dto.PedidoRequestDTO;
import com.controlePedidos.application.mapper.PedidoMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public synchronized Pedido efetuarPedido(PedidoRequestDTO dto) {
        List<ProdutoSolicitado> produtosSolicitados = PedidoMapper.toProdutos(dto.getProdutos());
        List<Produto> produtos = new ArrayList<>();
        List<ProdutoSolicitado> produtosValidados = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        // Valida, atualiza estoque e acumula total
        for (ProdutoSolicitado solicitado : produtosSolicitados) {
            String descricao = solicitado.getDescricao();
            int quantidade = solicitado.getQuantidade();

            Optional<Produto> produtoEncontrado  = produtoRepository.buscarPorDescricao(descricao);
            if (produtoEncontrado.isEmpty()) {
                throw new IllegalArgumentException("Produto não encontrado: " + descricao);
            }

            Produto produto = produtoEncontrado.get();
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new IllegalArgumentException("Estoque insuficiente para: " + descricao);
            }

            // Atualiza estoque
            produto.removerDoEstoque(quantidade);
            produtoRepository.salvar(produto);

            // Acumula valor total
            valorTotal = valorTotal.add(produto.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade)));

            // Adiciona à lista
            produtosValidados.add(new ProdutoSolicitado(
                    produto.getDescricao(),
                    quantidade,
                    produto.getPrecoUnitario()
            ));
        }

        // Cria o pedido
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setStatus(StatusPedido.ATIVO);
        pedido.setDataHora(LocalDateTime.now().toString());
        pedido.setProdutos(produtosValidados);
        pedido.setValorTotal(valorTotal);

        // Salva e retorna
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    public void cancelarPedido(UUID pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

        pedido.cancelar();

        // Repor o estoque do produto
        for (ProdutoSolicitado item : pedido.getProdutos()) {
            produtoRepository.buscarPorDescricao(item.getDescricao()).ifPresent(produto -> {
                produto.adicionarAoEstoque(item.getQuantidade());
                produtoRepository.salvar(produto);
            });
        }

        // Persistir o pedido atualizado
        pedidoRepository.salvar(pedido);
    }

//    // Listar Pedidos Ativos
//    public List<Pedido> listarPedidosAtivos() {
//        return pedidoRepository.listarTodos()
//                .stream()
//                .filter(p -> p.getStatus() == StatusPedido.ATIVO)
//                .toList();
//    }
}
