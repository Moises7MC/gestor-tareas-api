package pe.jllalle.gestortareasapi.dto;

import pe.jllalle.gestortareasapi.entity.EstadoTarea;
import pe.jllalle.gestortareasapi.entity.Tarea;

import java.time.LocalDate;

public record TareaResponse(
        Long id,
        String titulo,
        String descripcion,
        EstadoTarea estado,
        LocalDate fechaLimite,
        Long proyectoId,
        String proyectoNombre,
        Long asignadoAId,
        String asignadoANombre
) {
    public static TareaResponse desde(Tarea tarea) {
        Long asignadoAId = tarea.getAsignadoA() != null ? tarea.getAsignadoA().getId() : null;
        String asignadoANombre = tarea.getAsignadoA() != null ? tarea.getAsignadoA().getNombre() : null;

        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getFechaLimite(),
                tarea.getProyecto().getId(),
                tarea.getProyecto().getNombre(),
                asignadoAId,
                asignadoANombre
        );
    }
}