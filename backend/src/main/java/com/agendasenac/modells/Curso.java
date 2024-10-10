package com.agendasenac.modells;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Curso")
public class Curso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "idcursos")
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idcursos;
	
	@Column(name = "nomecurso")
	private String nomecurso;
	
	@Column(name = "datalhescurso")
	private String datalhescurso;
	


	public Long getIdcursos() {
		return idcursos;
	}

	public void setIdcursos(Long idcursos) {
		this.idcursos = idcursos;
	}

	public String getNomecurso() {
		return nomecurso;
	}

	public void setNomecurso(String nomecurso) {
		this.nomecurso = nomecurso;
	}

	public String getDatalhescurso() {
		return datalhescurso;
	}

	public void setDatalhescurso(String datalhescurso) {
		this.datalhescurso = datalhescurso;
	}
	
	
	
	
	

}
