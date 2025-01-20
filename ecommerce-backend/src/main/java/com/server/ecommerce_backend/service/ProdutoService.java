package com.server.ecommerce_backend.service;

import com.server.ecommerce_backend.entity.Produto;
import com.server.ecommerce_backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(UUID id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public void deletar(UUID id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizar(UUID id, Produto produtoAtualizado) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setCategoria(produtoAtualizado.getCategoria());
            return produtoRepository.save(produto);
        }
        return null;
    }
}
