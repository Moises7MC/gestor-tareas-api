package pe.jllalle.gestortareasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CrearTareaRequest(
        @NotBlank String titulo,
        String descripcion,
        LocalDate fechaLimite,
        @NotNull Long proyectoId
) {}

