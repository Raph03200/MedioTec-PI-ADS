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
	public ResponseEntity<Turma> UmaTurma(@PathVariable Long idturma) {
		Optional<Turma> turma = Optional.ofNullable(tr.findByidturma(idturma));
		if (turma.isPresent()) {
			return ResponseEntity.ok(turma.get());
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
	@PostMapping("/turmas")
	@CrossOrigin
	public ResponseEntity<String> CriarTurma(@RequestBody Turma turma) {
		tr.save(turma);
		return ResponseEntity.status(HttpStatus.CREATED).body("Turma cadastrado com sucesso");
	}
	
	@DeleteMapping("/turma/{idturma}")
	@CrossOrigin
	public ResponseEntity<String> DeleteTurma(@PathVariable Long idturma) {
		if (tr.existsById(idturma)){
			tr.deleteById(idturma);
			return ResponseEntity.status(HttpStatus.OK).body("Turma deletado com sucesso");
		}
		else {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrado");
		}
	}
	
	@PutMapping("/turma/{idturma}")
	@CrossOrigin
	public ResponseEntity<String> atualizarTurma(@PathVariable Long idturma, @RequestBody Map<String, Object> updates) {
	    Optional<Turma> optionalTurma = Optional.ofNullable(tr.findByidturma(idturma));
	    
	    if (optionalTurma.isPresent()) {
	        Turma turma = optionalTurma.get();
	        
	        updates.forEach((key, value) -> {
	            try {
	                Field field = Turma.class.getDeclaredField(key);
	                field.setAccessible(true);
	                field.set(turma, value);
	            } catch (NoSuchFieldException e) {
	                // Log de aviso ou mensagem para campo não encontrado
	                System.out.println("Campo não encontrado: " + key);
	            } catch (IllegalAccessException e) {
	                return;
	            }
	        });
	        
	        tr.save(turma);
	        return ResponseEntity.ok("Turma atualizada com sucesso");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada");
	    }
	}

	
	
	
	
		
}
