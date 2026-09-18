package pe.jllalle.gestortareasapi.dto;

import pe.jllalle.gestortareasapi.entity.Proyecto;

public record ProyectoResponse(
        Long id,
        String nombre,
        String descripcion,
        Long propietarioId,
        String propietarioNombre
) {
    public static ProyectoResponse desde(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getPropietario().getId(),
                proyecto.getPropietario().getNombre()
        );
    }
}