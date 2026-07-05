package com.duoc.plataformaEducativa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.plataformaEducativa.dto.InscripcionDTO;
import com.duoc.plataformaEducativa.service.InscripcionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/inscripciones")
public class InscripcionController {

	private final InscripcionService inscripcionService;

	public InscripcionController(InscripcionService inscripcionService) {
		this.inscripcionService = inscripcionService;
	}

	// obtiene todas las inscripciones
	@GetMapping
	public ResponseEntity<List<InscripcionDTO>> listarInscripciones() {
		return ResponseEntity.ok(inscripcionService.listarInscripciones());
	}

	// obtiene inscripciones por curso
	@GetMapping("/curso/{cursoId}")
	public ResponseEntity<List<InscripcionDTO>> listarInscripcionesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(inscripcionService.listarInscripcionesPorCurso(cursoId));
	}

	@PostMapping
	public ResponseEntity<Boolean> crearInscipcion(@Valid @RequestBody InscripcionDTO inscripcion){
		boolean nuevaInscripcion = inscripcionService.crearInscipcion(inscripcion);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
	}

	/*Descargar resumen inscripcion
	@GetMapping("/resumen/{id}")
	public ResponseEntity<byte[]> obtenerResumenInscripcion(@PathVariable Long id){
		byte[] archivo = inscripcionService.generarResumenInscripcion(id);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "resumen-inscripcion-" + id + ".txt" + "\"")
        .contentType(MediaType.APPLICATION_OCTET_STREAM).body(archivo);
	}*/
}