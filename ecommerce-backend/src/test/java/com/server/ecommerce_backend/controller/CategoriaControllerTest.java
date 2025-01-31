package com.server.ecommerce_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.ecommerce_backend.entity.Categoria;
import com.server.ecommerce_backend.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CategoriaService categoriaService;

    @Test
    void testCriarCategoria() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral.");

        when(categoriaService.criar(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Eletrônicos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Produtos eletrônicos em geral."));
        verify(categoriaService, times(1)).criar(any(Categoria.class));
    }

    @Test
    void testBuscarCategoriaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria(id, "Livros", "Livros de todos os gêneros.");

        when(categoriaService.buscarPorId(id)).thenReturn(categoria);

        mockMvc.perform(MockMvcRequestBuilders.get("/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Livros"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Livros de todos os gêneros."));
        verify(categoriaService, times(1)).buscarPorId(id);
    }

    @Test
    void testBuscarCategoriaPorIdNaoEncontrada() throws Exception {
        UUID id = UUID.randomUUID();

        when(categoriaService.buscarPorId(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(categoriaService, times(1)).buscarPorId(id);
    }

    @Test
    void testListarCategorias() throws Exception {
        Categoria categoria1 = new Categoria(UUID.randomUUID(), "Eletrônicos", "Produtos eletrônicos em geral.");
        Categoria categoria2 = new Categoria(UUID.randomUUID(), "Livros", "Livros de todos os gêneros.");

        when(categoriaService.listar()).thenReturn(Arrays.asList(categoria1, categoria2));

        mockMvc.perform(MockMvcRequestBuilders.get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Eletrônicos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Livros"));
        verify(categoriaService, times(1)).listar();
    }

    @Test
    void testAtualizarCategoria() throws Exception {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Livros Atualizados", "Livros de todos os gêneros, atualizado.");

        when(categoriaService.atualizar(eq(id), any(Categoria.class))).thenReturn(categoriaAtualizada);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaAtualizada)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Livros Atualizados"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Livros de todos os gêneros, atualizado."));
        verify(categoriaService, times(1)).atualizar(eq(id), any(Categoria.class));
    }

    @Test
    void testAtualizarCategoriaNaoEncontrada() throws Exception {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Livros Atualizados", "Livros de todos os gêneros, atualizado.");

        when(categoriaService.atualizar(eq(id), any(Categoria.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaAtualizada)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(categoriaService, times(1)).atualizar(eq(id), any(Categoria.class));

    }

    @Test
    void testDeletarCategoria() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(categoriaService).deletar(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/categorias/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(categoriaService, times(1)).deletar(id);
    }
}
