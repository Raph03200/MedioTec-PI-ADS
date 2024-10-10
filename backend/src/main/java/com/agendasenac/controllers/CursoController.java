package com.agendasenac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agendasenac.modells.Curso;
import com.agendasenac.repository.CursoRepository;

@RestController
public class CursoController {
	
	@Autowired
	private CursoRepository cr;

	@GetMapping("/cursos")
	public Iterable<Curso> curso() {
		return cr.findAll();
	}
	
	@PostMapping("/cursos")
	@CrossOrigin(origins = "*") 
	public ResponseEntity<String> cadastrocurso(@RequestBody Curso curso) {
	        cr.save(curso);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso");
	}
	
}
