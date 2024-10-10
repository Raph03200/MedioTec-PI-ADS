package com.agendasenac.repository;

import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.ComunicaDoUser;


public interface ComunicadosRepository extends CrudRepository<ComunicaDoUser, Long>  {
	
	ComunicaDoUser findByIdComunicado(Long IdComunicado);

}
