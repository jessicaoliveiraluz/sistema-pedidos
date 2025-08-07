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
    private final ObjectMapper mapper;

    public JsonPedidoRepository() {
        this.mapper = new ObjectMapper();
    }

    public JsonPedidoRepository(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void salvarPedido(Pedido pedido) {
        try {
            List<Pedido> pedidos = listarPedidosDoArquivo();

            boolean atualizado = false;
            for (int i = 0; i < pedidos.size(); i++) {
                if (pedidos.get(i).getId().equals(pedido.getId())) {
                    pedidos.set(i, pedido);
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

    public List<Pedido> listarPedidosDoArquivo() {
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

    @Override
    public Optional<Pedido> buscarPedidoPorId(UUID id) {
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

    @Override
    public List<Pedido> listarPedidosAtivos() {
        try {
            File file = new File(CAMINHO_ARQUIVO_PEDIDOS);
            if (!file.exists()) return new ArrayList<>();

            return mapper.readValue(file, new TypeReference<List<Pedido>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler pedidos", e);
        }
    }
}
