package com.server.ecommerce_backend.service;

import com.server.ecommerce_backend.entity.Categoria;
import com.server.ecommerce_backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(UUID id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public void deletar(UUID id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria atualizar(UUID id, Categoria categoriaAtualizada) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            categoria.setNome(categoriaAtualizada.getNome());
            categoria.setDescricao(categoriaAtualizada.getDescricao());
            return categoriaRepository.save(categoria);
        }
        return null;
    }
}
