package pe.jllalle.gestortareasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProyectoRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotNull Long propietarioId
) {}

