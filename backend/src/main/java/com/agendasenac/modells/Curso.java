package com.agendasenac.modells;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCurso")
    private Long idCurso;

    @Column(name = "nomeCurso")
    private String nomeCurso;

    @Column(name = "detalhesCurso")
    private String detalhesCurso;

    // Relacionamento de 1:N com Disciplinas
    @OneToMany(mappedBy = "curso") // 'curso' é a referência na classe Disciplinas
    private List<Disciplinas> disciplinas;

    // Getters e setters

    public List<Disciplinas> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplinas> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getDetalhesCurso() {
        return detalhesCurso;
    }

    public void setDetalhesCurso(String detalhesCurso) {
        this.detalhesCurso = detalhesCurso;
    }
}
