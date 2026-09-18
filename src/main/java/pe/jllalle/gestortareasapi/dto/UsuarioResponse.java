package pe.jllalle.gestortareasapi.dto;

import pe.jllalle.gestortareasapi.entity.Rol;
import pe.jllalle.gestortareasapi.entity.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        Rol rol
) {
    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}

