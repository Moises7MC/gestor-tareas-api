package pe.jllalle.gestortareasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jllalle.gestortareasapi.entity.Proyecto;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByPropietarioId(Long propietarioId);
}

