package com.agendasenac.repository;

import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.Turma;

public interface TurmaRepository extends CrudRepository<Turma, Long>{
	Turma findByidturma(Long idturma);
}
