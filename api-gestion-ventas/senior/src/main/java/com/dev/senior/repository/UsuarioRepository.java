package com.dev.senior.repository;

import com.dev.senior.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
}
