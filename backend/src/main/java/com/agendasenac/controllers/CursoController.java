package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agendasenac.modells.Curso;
import com.agendasenac.modells.Disciplinas;
import com.agendasenac.repository.CursoRepository;
import com.agendasenac.repository.DisciplinasRepository;

@RestController
@RequestMapping("/cursos")
@CrossOrigin
public class CursoController {

    @Autowired
    private CursoRepository cr;
    
    @Autowired
    private DisciplinasRepository dr;

    // Método para criar uma resposta padronizada
    private ResponseEntity<Map<String, Object>> createResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    // Listar todos os cursos
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarCursos() {
        Iterable<Curso> cursos = cr.findAll();
        if (!cursos.iterator().hasNext()) {
            return createResponse(HttpStatus.NOT_FOUND, "Nenhum curso encontrado", null);
        }
        return createResponse(HttpStatus.OK, "Cursos encontrados", cursos);
    }

    // Cadastrar novo curso
    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastroCurso(@RequestBody Curso curso) {
        Map<String, String> errors = validarCurso(curso);
        if (!errors.isEmpty()) {
            return createResponse(HttpStatus.BAD_REQUEST, "Erro na validação dos dados", errors);
        }
        try {
            cr.save(curso);
            return createResponse(HttpStatus.CREATED, "Curso cadastrado com sucesso", curso);
        } catch (Exception e) {
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar o curso", e.getMessage());
        }
    }

    // Atualizar curso
    @PutMapping("/{idCurso}")
    public ResponseEntity<Map<String, Object>> atualizarCurso(
            @PathVariable Long idCurso, 
            @RequestBody Map<String, Object> updates) {

        Optional<Curso> optionalCurso = Optional.ofNullable(cr.findByidCurso(idCurso));
        if (!optionalCurso.isPresent()) {
            return createResponse(HttpStatus.NOT_FOUND, "Curso não encontrado", null);
        }

        Curso curso = optionalCurso.get();
        Map<String, String> errors = new HashMap<>();

        updates.forEach((key, value) -> {
            try {
                if ("disciplinas".equals(key)) {
                    // Tratamento especial para a lista de disciplinas
                    List<Map<String, Object>> disciplinasList = (List<Map<String, Object>>) value;
                    List<Disciplinas> novasDisciplinas = new ArrayList<>();

                    for (Map<String, Object> disciplinaMap : disciplinasList) {
                        try {
                            // Verificação do campo idDisciplina
                            if (!disciplinaMap.containsKey("idDisciplina")) {
                                errors.put("disciplinas", "Campo 'idDisciplina' não encontrado na lista de disciplinas.");
                                continue;
                            }

                            Long idDisciplina = Long.valueOf(disciplinaMap.get("idDisciplina").toString());

                            // Verificar se a disciplina existe no banco de dados
                            Disciplinas disciplina = dr.findById(idDisciplina)
                                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada: " + idDisciplina));

                            disciplina.setCurso(curso); // Define o curso para a disciplina
                            novasDisciplinas.add(disciplina);
                        } catch (NumberFormatException e) {
                            errors.put("idDisciplina", "O ID da disciplina deve ser um número válido.");
                        } catch (IllegalArgumentException e) {
                            errors.put("disciplinas", e.getMessage()); // Captura a mensagem de disciplina não encontrada
                        }
                    }

                    // Adiciona a lista de disciplinas atualizadas ao curso
                    curso.setDisciplinas(novasDisciplinas);

                } else {
                    // Atualização dos campos simples
                    Field field = Curso.class.getDeclaredField(key);
                    field.setAccessible(true);

                    // Validações específicas
                    if ("nomeCurso".equals(key)) {
                        if (value == null || ((String) value).trim().isEmpty()) {
                            errors.put(key, "O nome do curso é obrigatório e não pode estar vazio.");
                        }
                    }

                    if ("detalhesCurso".equals(key)) {
                        if (value != null && ((String) value).length() < 5) {
                            errors.put(key, "Os detalhes do curso devem ter no mínimo 5 caracteres.");
                        }
                    }

                    // Atualiza o campo
                    field.set(curso, value);
                }
            } catch (NoSuchFieldException e) {
                errors.put(key, "Campo não encontrado: " + key);
            } catch (IllegalAccessException e) {
                errors.put(key, "Erro ao acessar o campo: " + key);
            } catch (ClassCastException e) {
                errors.put(key, "Tipo de dado inválido para o campo: " + key);
            }
        });

        // Retorna erros, se houver
        if (!errors.isEmpty()) {
            return createResponse(HttpStatus.BAD_REQUEST, "Erro na validação dos campos", errors);
        }

        // Tenta salvar o curso atualizado
        try {
            cr.save(curso);
            return createResponse(HttpStatus.OK, "Curso atualizado com sucesso", curso);
        } catch (Exception e) {
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar o curso", e.getMessage());
        }
    }



    // Método de validação do curso
    private Map<String, String> validarCurso(Curso curso) {
        Map<String, String> errors = new HashMap<>();

        if (curso.getNomeCurso() == null || curso.getNomeCurso().trim().isEmpty()) {
            errors.put("NomeDoCurso", "O nome do curso é obrigatório e não pode estar vazio.");
        }

        if (curso.getDetalhesCurso() != null && curso.getDetalhesCurso().length() < 5) {
            errors.put("DetalhesCurso", "Os detalhes do curso devem ter no mínimo 5 caracteres.");
        }

        return errors;
    }
}
