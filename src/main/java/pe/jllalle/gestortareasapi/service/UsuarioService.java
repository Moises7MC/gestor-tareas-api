package pe.jllalle.gestortareasapi.service;

import pe.jllalle.gestortareasapi.entity.Rol;
import pe.jllalle.gestortareasapi.entity.Usuario;

public interface UsuarioService {

    Usuario registrar(String nombre, String email, String password, Rol rol);

    Usuario buscarPorEmail(String email);
}

