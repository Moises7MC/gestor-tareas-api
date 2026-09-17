package pe.jllalle.gestortareasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jllalle.gestortareasapi.entity.EstadoTarea;
import pe.jllalle.gestortareasapi.entity.Tarea;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByProyectoId(Long proyectoId);

    List<Tarea> findByAsignadoAId(Long usuarioId);

    List<Tarea> findByEstado(EstadoTarea estado);
}

