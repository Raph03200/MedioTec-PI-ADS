package com.agendasenac.modells;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "Disciplinas")
public class Disciplinas implements Serializable {
    
    private static final long serialVersionUID = 1L;  // Corrigido o nome da variável

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDisciplina")
    private Long idDisciplina;

    @Column(name = "NomeDaDisciplina")
    private String nomeDaDisciplina;

    @Column(name = "DetalhesAdicionais")
    private String detalhesAdicionais;

    @Column(name = "CargaHoraria")
    private String cargaHoraria;

    @ManyToOne(optional = true)
    @JoinColumn(name = "professor_id")  // Adicionando nome de coluna explicitamente
    private UserSistema professor;

    @ManyToOne
    @JoinColumn(name = "curso_id")  // Adicionando nome de coluna explicitamente
    private Curso curso;

    // Getters e setters com nomes corrigidos
    public String getNomeProfessor() {
        return professor != null ? professor.getNomeCompletoUser() : "SEM NOME";
    }

    public String getContatoProfessor() {
        return professor != null ? professor.getContatopessoal() : "SEM CONTATO";
    }

    public Long getProfessorId() {
        return professor != null ? professor.getCodigo() : null;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setProfessor(UserSistema professor) {
        this.professor = professor;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDaDisciplina() {
        return nomeDaDisciplina;
    }

    public void setNomeDaDisciplina(String nomeDaDisciplina) {
        this.nomeDaDisciplina = nomeDaDisciplina;
    }

    public String getDetalhesAdicionais() {
        return detalhesAdicionais;
    }

    public void setDetalhesAdicionais(String detalhesAdicionais) {
        this.detalhesAdicionais = detalhesAdicionais;
    }
}
