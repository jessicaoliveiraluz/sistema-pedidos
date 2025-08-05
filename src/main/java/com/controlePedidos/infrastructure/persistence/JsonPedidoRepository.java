package com.controlePedidos.infrastructure.persistence;

import com.controlePedidos.domain.model.Pedido;
import com.controlePedidos.domain.repository.PedidoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JsonPedidoRepository implements PedidoRepository {

    private static final String CAMINHO_ARQUIVO_PEDIDOS = "./src/main/resources/db/pedidos.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void salvar(Pedido pedido) {
        try {
            List<Pedido> pedidos = listarPedidosDoArquivo();

            boolean atualizado = false;
            for (int i = 0; i < pedidos.size(); i++) {
                if (pedidos.get(i).getId().equals(pedido.getId())) {
                    pedidos.set(i, pedido); // Atualiza o pedido existente
                    atualizado = true;
                    break;
                }
            }
            if (!atualizado) {
                pedidos.add(pedido);
            }
            mapper.writeValue(new File(CAMINHO_ARQUIVO_PEDIDOS), pedidos);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage(), e);
        }
    }

    private List<Pedido> listarPedidosDoArquivo() {
        try {
            File file = new File(CAMINHO_ARQUIVO_PEDIDOS);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Pedido>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler pedidos do arquivo: " + e.getMessage(), e);
        }
    }


//    @Override
//    public void salvar(Pedido pedido) {
//        List<Pedido> pedidos = listarTodos();
//        pedidos.removeIf(p -> p.getId().equals(pedido.getId())); // Atualiza se já existir
//        pedidos.add(pedido);
//        salvarTodos(pedidos);
//    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        try {
            File file = new File(CAMINHO_ARQUIVO_PEDIDOS);
            if (!file.exists()) return Optional.empty();

            List<Pedido> pedidos = mapper.readValue(file, new TypeReference<List<Pedido>>() {
            });
            return pedidos.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedido por ID", e);
        }
    }

//    @Override
//    public List<Pedido> listarTodos() {
//        try {
//            File file = new File(CAMINHO_ARQUIVO_PEDIDOS);
//            if (!file.exists()) return new ArrayList<>();
//
//            List<Pedido> todosPedidos = mapper.readValue(file, new TypeReference<List<Pedido>>() {});
//            return todosPedidos.stream()
//                    .filter(Pedido::isAtivo) // mantém apenas os pedidos com status diferente de CANCELADO
//                    .collect(Collectors.toList());
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao ler pedidos", e);
//        }
//    }

//    private void salvarTodos(List<Pedido> pedidos) {
//        try {
//            File file = new File("data");
//            if (!file.exists()) file.mkdirs();
//            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), pedidos);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao salvar pedidos", e);
//        }
//    }
}
