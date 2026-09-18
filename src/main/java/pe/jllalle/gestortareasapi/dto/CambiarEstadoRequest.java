package pe.jllalle.gestortareasapi.dto;

import jakarta.validation.constraints.NotNull;
import pe.jllalle.gestortareasapi.entity.EstadoTarea;

public record CambiarEstadoRequest(@NotNull EstadoTarea estado) {}

