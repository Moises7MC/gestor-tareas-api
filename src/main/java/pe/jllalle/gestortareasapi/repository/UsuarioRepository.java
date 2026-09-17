package pe.jllalle.gestortareasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jllalle.gestortareasapi.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}

