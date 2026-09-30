package com.dev.senior.service;

import com.dev.senior.repository.UsuarioRepository;
import com.dev.senior.model.*;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrar(Usuario usuario){
        if(usuarioRepository.existsByCorreo(usuario.getCorreo())){
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario.setRol(Rol.USER);

        return usuarioRepository.save(usuario);

    }

    public Usuario guardar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }

}
