package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;

//@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario){
        if(usuario.getNombre() == null){
            throw new NullPointerException("Nombre nulo - ojo con eso mi papacho");
        }

        return usuarioRepository.guardarUsuario(usuario);
    }
}
