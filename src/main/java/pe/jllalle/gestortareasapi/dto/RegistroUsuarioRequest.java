package pe.jllalle.gestortareasapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.jllalle.gestortareasapi.entity.Rol;

public record RegistroUsuarioRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
        @NotNull Rol rol
) {}

