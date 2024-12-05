package com.agendasenac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agendasenac.modells.Disciplinas;

public interface DisciplinasRepository extends JpaRepository<Disciplinas, Long> {

    Disciplinas findByIdDisciplina(Long idDisciplina);
    
    List<Disciplinas> findByProfessorCodigo(Long codigo);

    // Consulta corrigida, considerando o relacionamento correto
    @Query("SELECT d FROM Disciplinas d WHERE d.professor.codigo = :codigo AND d.curso.idCurso = :idCurso")
    List<Disciplinas> findByProfessorCodigoAndCursoId(@Param("codigo") Long codigo, @Param("idCurso") Long idCurso);
}
