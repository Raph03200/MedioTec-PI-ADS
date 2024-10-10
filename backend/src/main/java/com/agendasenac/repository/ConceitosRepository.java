package com.agendasenac.repository;

import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.Conceitos;

public interface ConceitosRepository extends CrudRepository<Conceitos, Long>{
	Conceitos findByIdConceito(Long IdConceito);
}
