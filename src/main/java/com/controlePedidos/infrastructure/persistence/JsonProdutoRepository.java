package com.controlePedidos.infrastructure.persistence;

import com.controlePedidos.domain.model.Produto;
import com.controlePedidos.domain.repository.ProdutoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonProdutoRepository implements ProdutoRepository {

    private static final String CAMINHO_ARQUIVO_PRODUTOS = "./src/main/resources/db/produtos.json";
    private ObjectMapper mapper = new ObjectMapper();

    public JsonProdutoRepository(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void salvarProduto(Produto produto) {
        try {
            List<Produto> produtos = listarProdutosDoArquivo();

            // Substitui o produto com a mesma descrição
            boolean atualizado = false;
            for (int i = 0; i < produtos.size(); i++) {
                Produto p = produtos.get(i);
                if (p.getDescricao().equalsIgnoreCase(produto.getDescricao())) {
                    produtos.set(i, produto); // Atualiza o produto
                    atualizado = true;
                    break;
                }
            }

            if (!atualizado) {
                produtos.add(produto);
            }

            mapper.writeValue(new File(CAMINHO_ARQUIVO_PRODUTOS), produtos);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Produto> buscarProdutoPorDescricao(String descricao) {
        try {
            List<Produto> produtos = listarProdutosDoArquivo();
            return produtos.stream()
                    .filter(p -> p.getDescricao().equalsIgnoreCase(descricao))
                    .findFirst();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
    }

    private List<Produto> listarProdutosDoArquivo() {
        try {
            File file = new File(CAMINHO_ARQUIVO_PRODUTOS);
            file.listFiles();
            if (!file.exists()) {
                throw new RuntimeException("Arquivo de produtos não encontrado.");
            }
            return mapper.readValue(file, new TypeReference<List<Produto>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler produtos do arquivo: " + e.getMessage(), e);
        }
    }
}
