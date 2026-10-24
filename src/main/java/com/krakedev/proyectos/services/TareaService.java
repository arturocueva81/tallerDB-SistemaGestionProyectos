package com.krakedev.proyectos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public Tarea guardar(Tarea tarea) {
        try {
            return tareaRepository.save(tarea);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la tarea: " + e.getMessage());
        }
    }

    public List<Tarea> listar() {
        try {
            return tareaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las tareas: " + e.getMessage());
        }
    }
}