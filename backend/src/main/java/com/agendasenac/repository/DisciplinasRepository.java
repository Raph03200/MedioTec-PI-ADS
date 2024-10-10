package com.agendasenac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.agendasenac.modells.Disciplinas;

public interface DisciplinasRepository extends CrudRepository<Disciplinas, Long>{
			Disciplinas findByidDisciplina(Long idDisciplina);
			List<Disciplinas> findByProfessorCodigo(Long codigo);
			@Query("SELECT d FROM Disciplinas d WHERE d.professor.codigo = :codigo AND d.turma.idturma = :idturma")
		    List<Disciplinas> findByProfessorCodigoTurma(@Param("codigo") Long codigo, @Param("idturma") Long idturma);
			
}			
