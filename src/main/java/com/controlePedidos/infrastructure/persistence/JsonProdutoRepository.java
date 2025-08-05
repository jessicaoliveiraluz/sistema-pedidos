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

    @Override
    public void salvar(Produto produto) {
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

            // Se não encontrou, adiciona novo (opcional, mas útil)
            if (!atualizado) {
                produtos.add(produto);
            }

            // Salva a lista atualizada no arquivo
            mapper.writeValue(new File(CAMINHO_ARQUIVO_PRODUTOS), produtos);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Produto> buscarPorDescricao(String descricao) {
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
            //(new File("./src/main/java/com/controlePedidos/db")).listFiles()
            if (!file.exists()) {
                throw new RuntimeException("Arquivo de produtos não encontrado.");
            }
            return mapper.readValue(file, new TypeReference<List<Produto>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler produtos do arquivo: " + e.getMessage(), e);
        }
    }

//    @Override
//    public void salvar(Produto produto) {
//        List<Produto> produtos = listarTodos();
//        produtos.removeIf(p -> p.getId().equals(produto.getId())); // Atualiza se já existir
//        produtos.add(produto);
//        salvarTodos(produtos);
//    }

//    @Override
//    public List<Produto> listarTodos() {
//        try {
//            File file = new File(FILE_PATH);
//            if (!file.exists()) return new ArrayList<>();
//            return mapper.readValue(file, new TypeReference<List<Produto>>() {});
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao ler produtos", e);
//        }
//    }

//    private void salvarTodos(List<Produto> produtos) {
//        try {
//            File file = new File("data");
//            if (!file.exists()) file.mkdirs();
//            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), produtos);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao salvar produtos", e);
//        }
//    }
}
