package pe.jllalle.gestortareasapi.service;

import pe.jllalle.gestortareasapi.entity.EstadoTarea;
import pe.jllalle.gestortareasapi.entity.Tarea;

import java.time.LocalDate;
import java.util.List;

public interface TareaService {

    Tarea crear(String titulo, String descripcion, LocalDate fechaLimite, Long proyectoId);

    Tarea buscarPorId(Long id);

    Tarea asignarUsuario(Long tareaId, Long usuarioId);

    Tarea cambiarEstado(Long tareaId, EstadoTarea nuevoEstado);

    List<Tarea> listarPorProyecto(Long proyectoId);

    List<Tarea> listarPorUsuarioAsignado(Long usuarioId);
}

