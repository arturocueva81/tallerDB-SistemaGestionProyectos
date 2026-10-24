package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
	//controller de tarea
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody Tarea tarea) {
        try {
            Tarea guardada = tareaService.guardar(tarea);
            return ResponseEntity.ok(guardada);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al guardar la tarea: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        try {
            List<Tarea> tareas = tareaService.listar();
            return ResponseEntity.ok(tareas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al listar las tareas: " + e.getMessage());
        }
    }
}