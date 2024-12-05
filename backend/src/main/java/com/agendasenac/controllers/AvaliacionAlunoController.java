package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import com.agendasenac.modells.AvaliandoALuno;
import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.AvaliacionALunoRepository;
import com.agendasenac.repository.UserSistemaRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/avaliacions")
@CrossOrigin
public class AvaliacionAlunoController {

    @Autowired
    private AvaliacionALunoRepository Aar;

    @Autowired
    private UserSistemaRepository userSistemaRepository;

    private ResponseEntity<Map<String, Object>> createResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarAvaliacoes() {
        List<AvaliandoALuno> avaliacoes = (List<AvaliandoALuno>) Aar.findAll();
        if (avaliacoes.isEmpty()) {
            return createResponse(HttpStatus.NOT_FOUND, "Nenhuma avaliação encontrada", null);
        }
        return createResponse(HttpStatus.OK, "Avaliações encontradas", avaliacoes);
    }

    @GetMapping("/todas/{codigo}")
    public ResponseEntity<Map<String, Object>> getAvaliacoesPorAluno(@PathVariable Long codigo) {
        List<AvaliandoALuno> avaliacoes = Aar.findByAluno_Codigo(codigo);
        if (avaliacoes.isEmpty()) {
            return createResponse(HttpStatus.NOT_FOUND, "Nenhuma avaliação encontrada para este aluno", null);
        }
        return createResponse(HttpStatus.OK, "Avaliações encontradas", avaliacoes);
    }

    @GetMapping("/{idavalicacion}/{codigo}")
    public ResponseEntity<Map<String, Object>> ReceberAvaliacaoPorUser(@PathVariable Long idavalicacion, UserSistema codigo) {
        Optional<List<AvaliandoALuno>> AvalindoUno = Optional.ofNullable(Aar.findByIdavalicacionAndAluno(idavalicacion, codigo));
        if (AvalindoUno.isEmpty() || AvalindoUno.get().isEmpty()) {
            return createResponse(HttpStatus.NOT_FOUND, "Avaliação não encontrada para o aluno especificado", null);
        }
        return createResponse(HttpStatus.OK, "Avaliação encontrada", AvalindoUno.get());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> inserirConceito(@RequestBody AvaliandoALuno avaliandoALuno) {
        try {
            UserSistema aluno = userSistemaRepository.findById(avaliandoALuno.getAluno().getCodigo())
                    .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
            avaliandoALuno.setAluno(aluno);
            Aar.save(avaliandoALuno);
            return createResponse(HttpStatus.CREATED, "Avaliação inserida com sucesso", avaliandoALuno);
        } catch (EntityNotFoundException e) {
            return createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        } catch (Exception e) {
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao inserir avaliação", e.getMessage());
        }
    }

    @PatchMapping("/{idavalicacion}")
    public ResponseEntity<Map<String, Object>> atualizarAvaliacao(@PathVariable Long idavalicacion, @RequestBody Map<String, Object> updates) {
        try {
            AvaliandoALuno avaliando = Aar.findByidavalicacion(idavalicacion);
            if (avaliando == null) {
                return createResponse(HttpStatus.NOT_FOUND, "Avaliação não encontrada", null);
            }

            updates.forEach((key, value) -> {
                try {
                    Field field = ReflectionUtils.findField(AvaliandoALuno.class, key);
                    if (field != null) {
                        field.setAccessible(true);
                        // Conversão automática de Integer para Long, se necessário
                        if (field.getType().equals(Long.class) && value instanceof Integer) {
                            value = Long.valueOf((Integer) value);
                        }
                        ReflectionUtils.setField(field, avaliando, value);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao atualizar o campo: " + key, e);
                }
            });

            Aar.save(avaliando);
            return createResponse(HttpStatus.OK, "Avaliação atualizada com sucesso", avaliando);
        } catch (RuntimeException e) {
            return createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        } catch (Exception e) {
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar avaliação", e.getMessage());
        }
    }

    @DeleteMapping("/{idavalicacion}")
    public ResponseEntity<Map<String, Object>> deletarAvaliacao(@PathVariable Long idavalicacion) {
        try {
            if (Aar.existsById(idavalicacion)) {
                Aar.deleteById(idavalicacion);
                return createResponse(HttpStatus.OK, "Avaliação deletada com sucesso", null);
            } else {
                return createResponse(HttpStatus.NOT_FOUND, "Avaliação não encontrada", null);
            }
        } catch (Exception e) {
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar avaliação", e.getMessage());
        }
    }
}
