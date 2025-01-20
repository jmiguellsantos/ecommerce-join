package com.server.ecommerce_backend.service;

import com.server.ecommerce_backend.entity.Pedido;
import com.server.ecommerce_backend.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido criar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(UUID id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public void deletar(UUID id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido atualizar(UUID id, Pedido pedidoAtualizado) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setDataCriacao(pedidoAtualizado.getDataCriacao());
            pedido.setStatus(pedidoAtualizado.getStatus());
            pedido.setProdutos(pedidoAtualizado.getProdutos());
            return pedidoRepository.save(pedido);
        }
        return null;
    }
}
