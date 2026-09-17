package pe.jllalle.gestortareasapi.service;

import pe.jllalle.gestortareasapi.entity.Proyecto;

import java.util.List;

public interface ProyectoService {

    Proyecto crear(String nombre, String descripcion, Long propietarioId);

    List<Proyecto> listarPorPropietario(Long propietarioId);

    Proyecto buscarPorId(Long id);
}

