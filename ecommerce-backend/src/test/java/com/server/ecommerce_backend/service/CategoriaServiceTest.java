package com.server.ecommerce_backend.service;

import com.server.ecommerce_backend.entity.Categoria;
import com.server.ecommerce_backend.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Produtos eletrônicos em geral.");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria categoriaCriada = categoriaService.criar(categoria);

        assertNotNull(categoriaCriada);
        assertEquals(categoria.getNome(), categoriaCriada.getNome());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void testBuscarCategoriaPorId() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Livros");
        categoria.setDescricao("Livros de todos os gêneros.");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        Categoria categoriaEncontrada = categoriaService.buscarPorId(id);

        assertNotNull(categoriaEncontrada);
        assertEquals(id, categoriaEncontrada.getId());
        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarCategoriaPorIdNaoEncontrada() {
        UUID id = UUID.randomUUID();

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        Categoria categoriaEncontrada = categoriaService.buscarPorId(id);

        assertNull(categoriaEncontrada);
        verify(categoriaRepository, times(1)).findById(id);

    }


    @Test
    void testListarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(UUID.randomUUID(), "Eletrônicos", "Produtos eletrônicos em geral."));
        categorias.add(new Categoria(UUID.randomUUID(), "Livros", "Livros de todos os gêneros."));

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> categoriasListadas = categoriaService.listar();

        assertNotNull(categoriasListadas);
        assertEquals(2, categoriasListadas.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void testDeletarCategoria() {
        UUID id = UUID.randomUUID();
        doNothing().when(categoriaRepository).deleteById(id);

        categoriaService.deletar(id);

        verify(categoriaRepository, times(1)).deleteById(id);
    }

    @Test
    void testAtualizarCategoria() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria(id, "Livros", "Livros de todos os gêneros.");
        Categoria categoriaAtualizada = new Categoria(id, "Livros Atualizados", "Livros de todos os gêneros, atualizado.");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaAtualizada);

        Categoria categoriaResult = categoriaService.atualizar(id, categoriaAtualizada);

        assertNotNull(categoriaResult);
        assertEquals(categoriaAtualizada.getNome(), categoriaResult.getNome());
        assertEquals(categoriaAtualizada.getDescricao(), categoriaResult.getDescricao());
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
    @Test
    void testAtualizarCategoriaNaoEncontrada() {
        UUID id = UUID.randomUUID();
        Categoria categoriaAtualizada = new Categoria(id, "Livros Atualizados", "Livros de todos os gêneros, atualizado.");

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        Categoria categoriaResult = categoriaService.atualizar(id, categoriaAtualizada);

        assertNull(categoriaResult);
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(0)).save(any(Categoria.class));
    }
}