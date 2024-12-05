package com.agendasenac.controllers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import com.agendasenac.modells.Turma;
import com.agendasenac.repository.TurmaRepository;

@RestController
public class TurmaController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private TurmaRepository tr;
    
    @GetMapping("/turmas")
    @CrossOrigin
    public Iterable<Turma> listturma() {
        return tr.findAll();
    }
    
    @GetMapping("/turma/{idturma}")
    @CrossOrigin
    public ResponseEntity<Object> UmaTurma(@PathVariable Long idturma) {
        Optional<Turma> turma = Optional.ofNullable(tr.findByidturma(idturma));
        if (turma.isPresent()) {
            return ResponseEntity.ok(turma.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Turma não encontrada", "ID da turma não corresponde a nenhum registro"));
        }
    }
    
    @PostMapping("/turmas")
    @CrossOrigin
    public ResponseEntity<Object> CriarTurma(@RequestBody @Validated Turma turma, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Se ocorrer erro de validação no corpo da requisição
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro de validação", errorMessages.toString()));
        }
        
        try {
            tr.save(turma);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Turma cadastrada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro interno", "Não foi possível cadastrar a turma. Tente novamente."));
        }
    }
    
    @DeleteMapping("/turma/{idturma}")
    @CrossOrigin
    public ResponseEntity<Object> DeleteTurma(@PathVariable Long idturma) {
        if (tr.existsById(idturma)){
            tr.deleteById(idturma);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Turma deletada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Turma não encontrada", "Nenhuma turma encontrada com o ID fornecido"));
        }
    }
    
    @PutMapping("/turma/{idturma}")
    @CrossOrigin
    public ResponseEntity<Object> atualizarTurma(@PathVariable Long idturma, @RequestBody Map<String, Object> updates) {
        Optional<Turma> optionalTurma = Optional.ofNullable(tr.findByidturma(idturma));
        
        if (optionalTurma.isPresent()) {
            Turma turma = optionalTurma.get();
            
            updates.forEach((key, value) -> {
                try {
                    Field field = Turma.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(turma, value);
                } catch (NoSuchFieldException e) {
                    System.out.println("Campo não encontrado: " + key);
                } catch (IllegalAccessException e) {
                    System.out.println("Erro ao acessar o campo: " + key);
                }
            });
            
            tr.save(turma);
            return ResponseEntity.ok(new SuccessResponse("Turma atualizada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Turma não encontrada", "Não foi possível encontrar a turma com o ID fornecido"));
        }
    }
    
    // Classes para respostas formatadas
    public static class ErrorResponse {
        private String error;
        private String message;
        
        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
        
        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
    
    public static class SuccessResponse {
        private String message;
        
        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    
    // Capturando erros de requisições malformadas ou JSON inválido
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Erro de formato", "O formato dos dados fornecidos está incorreto. Verifique e tente novamente."));
    }
}
