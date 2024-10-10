package com.agendasenac.repository;


import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.AvaliandoALuno;
import com.agendasenac.modells.Disciplinas;
import com.agendasenac.modells.UserSistema;

public interface AvaliacionALunoRepository extends CrudRepository<AvaliandoALuno, Long>{
	
	AvaliandoALuno findByidavalicacion(Long idavalicacion);
	
	List<AvaliandoALuno> findByIdavalicacionAndAluno(Long idavalicacion, UserSistema aluno);
	List<AvaliandoALuno> findByAluno_Codigo(Long codigo);

	}
