package com.agendasenac.repository;

import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.Curso;


public interface CursoRepository extends CrudRepository<Curso, Long>{
	
	Curso findByidCurso(Long idCurso);

}
