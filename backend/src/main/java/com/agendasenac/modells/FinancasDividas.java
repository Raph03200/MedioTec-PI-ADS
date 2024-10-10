package com.agendasenac.modells;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FinancasDividas")
public class FinancasDividas implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "idtransacao")
	private Long idtransacao;
	
	@Column(name = "urldocumento")
	private String urldocumento;
	
	@Column(name = "detalhes")
	private String detalhes;
	
	@Column(name = "datareferente")
	private String datareferente;
	
	@Column(name = "statuspagamento")
	private String statuspagamento;
	
	@ManyToOne
	private UserSistema usersistema;

	public Long getIdtransacao() {
		return idtransacao;
	}

	public void setIdtransacao(Long idtransacao) {
		this.idtransacao = idtransacao;
	}

	public String getUrldocumento() {
		return urldocumento;
	}

	public void setUrldocumento(String urldocumento) {
		this.urldocumento = urldocumento;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getDatareferente() {
		return datareferente;
	}

	public void setDatareferente(String datareferente) {
		this.datareferente = datareferente;
	}

	public String getStatuspagamento() {
		return statuspagamento;
	}

	public void setStatuspagamento(String statuspagamento) {
		this.statuspagamento = statuspagamento;
	} 
	
	

}
