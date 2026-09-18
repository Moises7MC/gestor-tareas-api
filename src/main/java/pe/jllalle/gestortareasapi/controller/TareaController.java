package pe.jllalle.gestortareasapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.jllalle.gestortareasapi.dto.AsignarUsuarioRequest;
import pe.jllalle.gestortareasapi.dto.CambiarEstadoRequest;
import pe.jllalle.gestortareasapi.dto.CrearTareaRequest;
import pe.jllalle.gestortareasapi.dto.TareaResponse;
import pe.jllalle.gestortareasapi.entity.Tarea;
import pe.jllalle.gestortareasapi.service.TareaService;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody CrearTareaRequest request) {
        Tarea tarea = tareaService.crear(request.titulo(), request.descripcion(), request.fechaLimite(), request.proyectoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(TareaResponse.desde(tarea));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(TareaResponse.desde(tareaService.buscarPorId(id)));
    }

    @PatchMapping("/{id}/asignar")
    public ResponseEntity<TareaResponse> asignarUsuario(@PathVariable Long id, @Valid @RequestBody AsignarUsuarioRequest request) {
        Tarea tarea = tareaService.asignarUsuario(id, request.usuarioId());
        return ResponseEntity.ok(TareaResponse.desde(tarea));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TareaResponse> cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambiarEstadoRequest request) {
        Tarea tarea = tareaService.cambiarEstado(id, request.estado());
        return ResponseEntity.ok(TareaResponse.desde(tarea));
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<TareaResponse>> listarPorProyecto(@PathVariable Long proyectoId) {
        List<TareaResponse> tareas = tareaService.listarPorProyecto(proyectoId).stream().map(TareaResponse::desde).toList();
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaResponse>> listarPorUsuarioAsignado(@PathVariable Long usuarioId) {
        List<TareaResponse> tareas = tareaService.listarPorUsuarioAsignado(usuarioId).stream().map(TareaResponse::desde).toList();
        return ResponseEntity.ok(tareas);
    }
}