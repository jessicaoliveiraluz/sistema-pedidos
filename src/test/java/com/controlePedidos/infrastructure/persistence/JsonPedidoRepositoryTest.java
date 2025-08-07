package com.controlePedidos.infrastructure.persistence;

import com.controlePedidos.domain.model.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonPedidoRepositoryTest {

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private JsonPedidoRepository repository;

    private Pedido pedido;

    @BeforeEach
    void setup() {
        pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
    }

    @Test
    void deveSalvarNovoPedido() {
        JsonPedidoRepository spyRepository = Mockito.spy(new JsonPedidoRepository());

        List<Pedido> pedidosExistente = new ArrayList<>();
        Pedido outroPedido = new Pedido();
        outroPedido.setId(UUID.randomUUID());
        pedidosExistente.add(outroPedido);

        doReturn(pedidosExistente).when(spyRepository).listarPedidosDoArquivo();

        spyRepository.salvarPedido(pedido);

        verify(spyRepository).salvarPedido(pedido);
    }

    @Test
    void deveBuscarPedidoPorIdQuandoExistir() throws Exception {
        UUID id = UUID.randomUUID();

        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(id);

        ObjectMapper mapperMock = mock(ObjectMapper.class);
        List<Pedido> pedidosNoArquivo = List.of(pedidoMock);

        // Simula o retorno da leitura do arquivo com a lista contendo o pedido
        when(mapperMock.readValue(
                any(File.class),
                ArgumentMatchers.<TypeReference<List<Pedido>>>any()
        )).thenReturn(pedidosNoArquivo);

        // Cria uma instância do repositório com ObjectMapper mockado
        JsonPedidoRepository repo = new JsonPedidoRepository(mapperMock);

        // Cria um arquivo mockado que existe
        File file = new File("./src/main/resources/db/pedidos.json");
        file.getParentFile().mkdirs(); // garante que pasta exista

        Optional<Pedido> resultado = repo.buscarPedidoPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
    }

    @Test
    void deveRetornarListaVaziaSeArquivoNaoExistir() {
        File file = new File("./src/main/resources/db/pedidos.json");
        if (file.exists()) {
            assertTrue(file.delete(), "Falha ao deletar o arquivo real para simular ausência.");
        }

        List<Pedido> pedidos = repository.listarPedidosAtivos();

        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());
    }
}
