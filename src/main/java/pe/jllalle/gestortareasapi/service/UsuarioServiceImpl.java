package pe.jllalle.gestortareasapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.jllalle.gestortareasapi.entity.Rol;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.exception.RecursoDuplicadoException;
import pe.jllalle.gestortareasapi.exception.RecursoNoEncontradoException;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(String nombre, String email, String password, Rol rol) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe un usuario con el email " + email);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró un usuario con el email " + email));
    }
}

