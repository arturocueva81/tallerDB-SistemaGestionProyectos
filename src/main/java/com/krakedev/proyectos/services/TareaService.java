package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

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
    
    public void eliminar(int id) {
        try {
            Optional<Tarea> resultado = tareaRepository.findById(id);
            if (resultado.isPresent()) {
                tareaRepository.deleteById(id);
            } else {
                throw new RuntimeException("Tarea no encontrada con id: " + id);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la tarea: " + e.getMessage());
        }
    }
}