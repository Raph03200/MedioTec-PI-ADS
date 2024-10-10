package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agendasenac.modells.AvaliandoALuno;
import com.agendasenac.modells.Disciplinas;
import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.AvaliacionALunoRepository;
import com.agendasenac.repository.UserSistemaRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class AvaliacionAlunoController{
	
	@Autowired
	private AvaliacionALunoRepository Aar;
	
	@Autowired
	private UserSistemaRepository userSistemaRepository;
	

	@GetMapping("/avaliacions")
	@CrossOrigin
	public Iterable<AvaliandoALuno> AvaliandoAluno() {
		return Aar.findAll();
	}
	
	
	@GetMapping("/avaliacions/todas/{codigo}")
	@CrossOrigin
	public ResponseEntity<List<AvaliandoALuno>> getAvaliacoesPorAluno(@PathVariable Long codigo) {

	    List<AvaliandoALuno> avaliacoes = Aar.findByAluno_Codigo(codigo);

	    if (!avaliacoes.isEmpty()) {
	        return ResponseEntity.ok(avaliacoes);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	
	
	@GetMapping("/avaliacions/{idavalicacion}/{codigo}")
	@CrossOrigin
	public ResponseEntity<List<AvaliandoALuno>> ReceberAvaliacaoPorUser(@PathVariable Long idavalicacion, UserSistema codigo) {

		
	    Optional<List<AvaliandoALuno>> AvalindoUno = Optional.ofNullable(Aar.findByIdavalicacionAndAluno(idavalicacion, codigo));

	    if (AvalindoUno.isPresent()) {
	        return ResponseEntity.ok(AvalindoUno.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

}
	

	

	@PostMapping("/avaliacions")
    @CrossOrigin
    public ResponseEntity<String> inserirConceito(@RequestBody AvaliandoALuno avaliandoALuno) {
        // Busca o aluno no banco para garantir que ele esteja sendo gerenciado pelo Hibernate
        UserSistema aluno = userSistemaRepository.findById(avaliandoALuno.getAluno().getCodigo())
            .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        // Atribui o aluno (que agora está gerenciado) à avaliação
        avaliandoALuno.setAluno(aluno);

        // Salva a avaliação no banco de dados
        Aar.save(avaliandoALuno);

        // Retorna uma resposta de sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body("Avaliação inserida com sucesso");
    }
	

	@PatchMapping("/avaliacions/{idavalicacion}")
	@CrossOrigin
	public ResponseEntity<String> updateUser(@PathVariable Long idavalicacion, @RequestBody Map<String, Object> updates) {
	        Optional<AvaliandoALuno> optionavaliando = Optional.ofNullable(Aar.findByidavalicacion(idavalicacion));

	        if (optionavaliando.isPresent()) {
	        	AvaliandoALuno avaliando = optionavaliando.get();

	            updates.forEach((key, value) -> {
	                try {
	                    if (value instanceof Map) {
	                        // Se o valor for um Map, então faz o tipo aninhado para tratar o objeto tipo turma
	                        Map<String, Object> nestedObject = (Map<String, Object>) value;
	                        Field field = ReflectionUtils.findRequiredField(UserSistema.class, key);
	                        if (field != null) {
	                            field.setAccessible(true);
	                            Object nestedInstance = field.getType().newInstance();
	                            nestedObject.forEach((nestedKey, nestedValue) -> {
	                                try {
	                                    Field nestedField = ReflectionUtils.findRequiredField(nestedInstance.getClass(), nestedKey);
	                                    if (nestedField != null) {
	                                        nestedField.setAccessible(true);
	                                        // Conversão de Integer para Long se necessário
	                                        if (nestedField.getType().equals(Long.class) && nestedValue instanceof Integer) {
	                                            nestedValue = Long.valueOf((Integer) nestedValue);
	                                        }
	                                        ReflectionUtils.setField(nestedField, nestedInstance, nestedValue);
	                                    }
	                                } catch (Exception e) {
	                                    throw new RuntimeException("Erro ao atualizar o campo aninhado: " + nestedKey, e);
	                                }
	                            });
	                            ReflectionUtils.setField(field, avaliando, nestedInstance);
	                        }
	                    } else {
	                        // Atualiza os campos simples
	                        Field field = ReflectionUtils.findRequiredField(UserSistema.class, key);
	                        if (field != null) {
	                            field.setAccessible(true);
	                            // Conversão de Integer para Long se necessário
	                            if (field.getType().equals(Long.class) && value instanceof Integer) {
	                                value = Long.valueOf((Integer) value);
	                            }
	                            ReflectionUtils.setField(field, avaliando, value);
	                        } else {
	                            throw new NoSuchFieldException("Campo não encontrado: " + key);
	                        }
	                    }
	                } catch (Exception e) {
	                    throw new RuntimeException("Erro ao atualizar o campo: " + key, e);
	                }
	            });

	            Aar.save(avaliando);
	            return ResponseEntity.status(HttpStatus.OK).body("Avaliação atualizada com sucesso");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrado");
	        }
	}
	
	
	@DeleteMapping("/avaliacions/{idavalicacion}")
	@CrossOrigin
	public ResponseEntity<String> userDelet(@PathVariable Long idavalicacion) {
	    if (Aar.existsById(idavalicacion)) {
	        Aar.deleteById(idavalicacion);
	        return ResponseEntity.status(HttpStatus.OK).body("Avaliação deletado com sucesso");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avaliação não encontrado");
	    }
	}

}
