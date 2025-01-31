package com.server.ecommerce_backend.service;

import com.server.ecommerce_backend.config.PasswordConfig;
import com.server.ecommerce_backend.entity.Usuario;
import com.server.ecommerce_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordConfig passwordConfig;

    public Usuario criar(Usuario usuario) {
        usuario.setPassword(passwordConfig.bCryptPasswordEncoder().encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void deletar(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setUsername(usuarioAtualizado.getUsername());
            usuario.setPassword(passwordConfig.bCryptPasswordEncoder().encode(usuarioAtualizado.getPassword()));
            usuario.setRole(usuarioAtualizado.getRole());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }
}