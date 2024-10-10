package com.agendasenac.modells;

import java.io.Serializable;
import java.util.List;

import com.agendasenac.services.RegraUsers;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "UserSistema")
public class UserSistema implements Serializable {
 

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "NomeCompletoUser")
	private String NomeCompletoUser;
	
	@Column(name = "CPF")
	private String cpfUser;
	
	public String getCpfUser() {
		return cpfUser;
	}
	public void setCpfUser(String cpfUser) {
		this.cpfUser = cpfUser;
	}
	@Column(name = "DataNascimentoUser")
	private String DataNascimentoUser;
	
	@Column(name = "GeneroUser")
	private String GeneroUser;
	
	@Column(name = "imailUser", unique = true)
	private String imailUser;
	
	@Column(name = "SenhaAcessoUser")
	@JsonIgnore
	private String SenhaAcessoUser;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TipoUser")
	private RegraUsers tipoUser;
	
	@Column(name = "ContatoPessoal")
	private String contatopessoal;
	
	@Column(name = "Parente")
	private String nomecontatoumergencia; // pais, namorada, esposa, marido e etc.
	
	@Column(name = "NumeroParente")
	private String numerourgencia;
	
	public String getContatopessoal() {
		return contatopessoal;
	}
	public void setContatopessoal(String contatopessoal) {
		this.contatopessoal = contatopessoal;
	}
	public String getNomecontatoumergencia() {
		return nomecontatoumergencia;
	}
	public void setNomecontatoumergencia(String nomecontatoumergencia) {
		this.nomecontatoumergencia = nomecontatoumergencia;
	}
	public String getNumerourgencia() {
		return numerourgencia;
	}
	public void setNumerourgencia(String numerourgencia) {
		this.numerourgencia = numerourgencia;
	}
	
	
	@ManyToOne(optional = true)
	private Turma turma;
	
	
	
	
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public Long getCodigo() {
		return codigo; 
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNomeCompletoUser() {
		return NomeCompletoUser;
	}
	public void setNomeCompletoUser(String nomeCompletoUser) {
		NomeCompletoUser = nomeCompletoUser;
	}
	public String getDataNascimentoUser() {
		return DataNascimentoUser;
	}
	public void setDataNascimentoUser(String dataNascimentoUser) {
		DataNascimentoUser = dataNascimentoUser;
	}
	public String getGeneroUser() {
		return GeneroUser;
	}
	public void setGeneroUser(String generoUser) {
		GeneroUser = generoUser;
	}
	public String getImailUser() {
		return imailUser;
	}
	public void setImailUser(String imailUser) {
		this.imailUser = imailUser;
	}
	
	public String getSenhaAcessoUser() {
		return SenhaAcessoUser;
	}
	public void setSenhaAcessoUser(String senhaAcessoUser) {
		this.SenhaAcessoUser = senhaAcessoUser;
	}
	public RegraUsers getTipoUser() {
		return tipoUser;
	}
	public void setTipoUser(RegraUsers tipoUser) {
		this.tipoUser = tipoUser;
	}
	
	

}
