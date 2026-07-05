package com.duoc.plataformaEducativa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.plataformaEducativa.dto.InscripcionDTO;
import com.duoc.plataformaEducativa.model.InscripcionEntity;
import com.duoc.plataformaEducativa.repository.InscripcionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InscripcionService {

	@Autowired
	private InscripcionRepository inscripcionRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private MensajeService mensajeService;


	public List<InscripcionDTO> listarInscripciones(){
		return inscripcionRepository.findAll().stream().map(this::toDTO).toList();
	}
	
	public List<InscripcionDTO> listarInscripcionesPorCurso(Long cursoId) {
		return inscripcionRepository.findByCurso_Id(cursoId).stream().map(this::toDTO).toList();
	}


	public boolean crearInscipcion(InscripcionDTO inscripcion){

		if (!verificarDatos(inscripcion)){
			return false;
		}

		// enviamos mensaje
		mensajeService.enviarObjeto(inscripcion);
		
		return true;
	}

	public boolean verificarDatos(InscripcionDTO inscripcion){
		if (cursoService.obtenerCursoId(inscripcion.getCursoId()) == null){
			return false;
		}
		if (usuarioService.obtenerUsuarioId(inscripcion.getEstudianteId()) == null){
			return false;
		}
		return true;
	}


	/*
	public byte[] generarResumenInscripcion(Long id) {
		byte[] archivo = null;
		InscripcionEntity inscripcion = inscripcionRepository.findById(id).orElse(null);
		try {
			archivo = FileService.generarResumenIncripcion(inscripcion);
		} catch (Exception e) {
			log.info("Error al generar resumen de inscipcion " + inscripcion.getId() + ": " + e.getMessage());
		}
		return archivo;
	}*/

	// to Entity / DTO
	private InscripcionDTO toDTO(InscripcionEntity inscripcion) {
		return new InscripcionDTO(
			inscripcion.getCurso() != null ? inscripcion.getCurso().getId() : null,
			inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getId() : null,
			inscripcion.getFechaInscripcion()
		);
	}

}
