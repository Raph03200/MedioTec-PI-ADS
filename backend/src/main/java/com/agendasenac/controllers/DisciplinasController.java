package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.HashMap;
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

import com.agendasenac.modells.Disciplinas;
import com.agendasenac.modells.Turma;
import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.DisciplinasRepository;

@RestController
public class DisciplinasController {
	
	@Autowired
	private DisciplinasRepository dr;
	
	@GetMapping("/disciplinas")
	@CrossOrigin
	public Iterable<Disciplinas> disiplinas() {
		return dr.findAll();
	}
	

	@GetMapping("/disciplinas/{idDisciplina}")
	@CrossOrigin
	public ResponseEntity<Disciplinas> RetornandoUmadiciplina(@PathVariable Long idDisciplina){
		Optional<Disciplinas> disiciplias = Optional.ofNullable(dr.findByidDisciplina(idDisciplina));
		
		if (disiciplias.isPresent()) {
	        return ResponseEntity.ok(disiciplias.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
		
	}
	
	
	
	@GetMapping("/disciplinas/professor/{codigo}")
	@CrossOrigin
	public ResponseEntity<List<Disciplinas>> RetornandoAsDisciplinasProfessor(@PathVariable Long codigo) {
	    List<Disciplinas> disciplinas = dr.findByProfessorCodigo(codigo);
	    
	    if (!disciplinas.isEmpty()) {
	        return ResponseEntity.ok(disciplinas);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	
	@GetMapping("/disciplinas/professor/{codigo}/{idturma}")
	@CrossOrigin
	public ResponseEntity<List<Disciplinas>> RetornandoAsDisciplinasProfessorTurma(
	        @PathVariable Long codigo,
	        @PathVariable Long idturma) {
	    List<Disciplinas> disciplinas = dr.findByProfessorCodigoTurma(codigo, idturma);
	    
	    if (!disciplinas.isEmpty()) {
	        return ResponseEntity.ok(disciplinas);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	
	@PostMapping("/disciplinas")
	@CrossOrigin
	public ResponseEntity<Map<String, String>> cadastroDisciplinas(@RequestBody Disciplinas disciplinas) {
	        
			dr.save(disciplinas);
	        // Cria um Map para retornar o JSON com a chave "text"
	        Map<String, String> response = new HashMap<>();
	        response.put("text", "Disciplina cadastrado com sucesso");
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
	
	@DeleteMapping("/disciplinas/{idDisciplina}")
	@CrossOrigin
	public ResponseEntity<Map<String, String>> deletedisciplinas(@PathVariable Long idDisciplina) {
	        Map<String, String> response = new HashMap<>();
	        
	        if (dr.existsById(idDisciplina)) {
	            dr.deleteById(idDisciplina);
	            
	            // Prepara o JSON de resposta com a mensagem de sucesso
	            response.put("text", "Disciplina deletada com sucesso");
	            return ResponseEntity.status(HttpStatus.OK).body(response);
	        } else {
	            // Prepara o JSON de resposta para quando a disciplina não é encontrada
	            response.put("text", "Disciplina não encontrada");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	}

	
	@PatchMapping("/disciplinas/{idDisciplina}")
	@CrossOrigin
	public ResponseEntity<String> atualizarTurma(@PathVariable Long idDisciplina, @RequestBody Map<String, Object> updates) {
	    Optional<Disciplinas> optionoptionaldisciplinaalTurma = Optional.ofNullable(dr.findByidDisciplina(idDisciplina));
	    
	    if (optionoptionaldisciplinaalTurma.isPresent()) {
	    	Disciplinas disciplinas = optionoptionaldisciplinaalTurma.get();
	        
	        updates.forEach((key, value) -> {
	            try {
	                Field field = Disciplinas.class.getDeclaredField(key);
	                field.setAccessible(true);
	                field.set(disciplinas, value);
	            } catch (NoSuchFieldException e) {
	                // Log de aviso ou mensagem para campo não encontrado
	                System.out.println("Campo não encontrado: " + key);
	            } catch (IllegalAccessException e) {
	                return;
	            }
	        });
	        
	        dr.save(disciplinas);
	        return ResponseEntity.ok("Disciplina atualizada com sucesso");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada");
	    }
	}
	
}
