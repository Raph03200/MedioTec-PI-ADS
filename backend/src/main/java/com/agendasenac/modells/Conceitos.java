package com.agendasenac.modells;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Conceitos")
public class Conceitos {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "idConceito")
	private Long idConceito;
	
	@Column(name = "NomeDoConceito")
	private String NomeDoConceito;
	
	@Column(name = "NotaConceito")
	private Double NotaConceito;
	
	
	public Long getIdConceito() {
		return idConceito;
	}
	public void setIdConceito(Long idConceito) {
		this.idConceito = idConceito;
	}
	public String getNomeDoConceito() {
		return NomeDoConceito;
	}
	public void setNomeDoConceito(String nomeDoConceito) {
		NomeDoConceito = nomeDoConceito;
	}
	public Double getNotaConceito() {
		return NotaConceito;
	}
	public void setNotaConceito(Double notaConceito) {
		NotaConceito = notaConceito;
	}
	
	

}
