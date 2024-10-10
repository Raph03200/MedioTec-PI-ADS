package com.agendasenac.modells;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.ManyToOne;

@Entity
public class AvaliandoALuno implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idavalicacion;
	
	@JoinColumn(name = "unidade")
	private String unidade;
	
	@JoinColumn(name = "ordemlancameneto")
	private String ordemlancameneto;
	
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getOrdemlancameneto() {
		return ordemlancameneto;
	}

	public void setOrdemlancameneto(String ordemlancameneto) {
		this.ordemlancameneto = ordemlancameneto;
	}

	public String getDataavalicion() {
		return dataavalicion;
	}

	public void setDataavalicion(String dataavalicion) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 this.dataavalicion = LocalDateTime.now().format(formatter);
	}

	@JoinColumn(name = "dataavalicion")
	private String dataavalicion;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idaluno")
	private UserSistema aluno;
	
	@ManyToOne
	@JoinColumn(name = "idprofessor")
	private UserSistema Professor;
	
	@ManyToOne
	@JoinColumn(name = "idconceito")
	private Conceitos conceito;
	
	@ManyToOne
	@JoinColumn(name = "iddisciplina")
	private Disciplinas disciplina;

	public Long getIdavalicacion() {
		return idavalicacion;
	}

	public void setIdavalicacion(Long idavalicacion) {
		this.idavalicacion = idavalicacion;
	}

	//
	
	public UserSistema getAluno() {
		return aluno;
	}
	
	public UserSistema getProfessor() {
		return Professor;
	}

	//
	public void setAluno(UserSistema aluno) {
		this.aluno = aluno;
	}


	public void setProfessor(UserSistema professor) {
		Professor = professor;
	}

	public Conceitos getConceito() {
		return conceito;
	}

	public void setConceito(Conceitos conceito) {
		this.conceito = conceito;
	}

	public Disciplinas getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplinas disciplina) {
		this.disciplina = disciplina;
	}
	

}
