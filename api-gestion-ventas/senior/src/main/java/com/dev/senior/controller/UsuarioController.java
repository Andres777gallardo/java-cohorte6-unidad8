package com.dev.senior.controller;

import com.dev.senior.service.*;
import com.dev.senior.dto.*;
import com.dev.senior.model.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping 
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioRequest request){
        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContraseña(request.getContrasena());
        usuario.setRol(Rol.USER);

       Usuario usuarioGuardado = usuarioService.registrar(usuario);

       UsuarioResponse response = new UsuarioResponse(
            usuarioGuardado.getId(),
            usuarioGuardado.getNombre(),
            usuarioGuardado.getCorreo(),
            usuarioGuardado.getRol().name()
       );

       return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping 
    public ResponseEntity<List<UsuarioResponse>> listar(){
        List<UsuarioResponse> usuarios = usuarioService.listarTodos()
                                            .stream()
                                            .map(usuario -> new UsuarioResponse(
                                                usuario.getId(),
                                                usuario.getNombre(),
                                                usuario.getCorreo(),
                                                usuario.getRol().name()
                                            )).toList();
        
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id)
            .map(usuario -> {
                    UsuarioResponse response = new UsuarioResponse(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol().name()
                    );
                    return ResponseEntity.ok(response);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
