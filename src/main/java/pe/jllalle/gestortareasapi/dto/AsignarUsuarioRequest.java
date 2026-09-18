package pe.jllalle.gestortareasapi.dto;

import jakarta.validation.constraints.NotNull;

public record AsignarUsuarioRequest(@NotNull Long usuarioId) {}

