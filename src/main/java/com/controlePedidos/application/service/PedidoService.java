package com.controlePedidos.application.service;

public class PedidoService {

//    private final PedidoRepository pedidoRepository;
//    private final ProdutoRepository produtoRepository;
//
//    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
//        this.pedidoRepository = pedidoRepository;
//        this.produtoRepository = produtoRepository;
//    }
//
//    public Pedido efetuarPedido(Long produtoId, Integer quantidade) {
//        Produto produto = produtoRepository.findById(produtoId)
//                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
//
//        if (produto.getEstoque() < quantidade) {
//            throw new RuntimeException("Estoque insuficiente");
//        }
//
//        produto.setEstoque(produto.getEstoque() - quantidade);
//        produtoRepository.save(produto);
//
//        Pedido pedido = new Pedido();
//        pedido.setDescricao("Pedido do produto " + produto.getNome());
//        pedido.setQuantidade(quantidade);
//        pedido.setPrecoUnitario(produto.getPrecoUnitario());
//        pedido.setStatus(StatusPedido.ATIVO);
//
//        return pedidoRepository.save(pedido);
//    }
}
