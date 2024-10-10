package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agendasenac.modells.Conceitos;
import com.agendasenac.repository.ConceitosRepository;

@RestController
public class ConceitosController {
	
	@Autowired
	private ConceitosRepository cp;
	
	@GetMapping("/conceito")
	@CrossOrigin
	public Iterable<Conceitos> ReceberConceitos() {
		return cp.findAll();
	}
	
	@GetMapping("conceito/{idConceito}")
	@CrossOrigin
	public ResponseEntity<Conceitos> RetornarUmConceito(@PathVariable Long idConceito) {
		Optional<Conceitos> conceito = Optional.ofNullable(cp.findByIdConceito(idConceito));
		
		 if (conceito.isPresent()) {
		        return ResponseEntity.ok(conceito.get());
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		    }
	}
	
	@PostMapping("/conceito")
	@CrossOrigin
	public ResponseEntity<String>  CriarConceito(@RequestBody Conceitos conceitos) {
		if (cp.save(conceitos) != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Conceito criado com sucesso");
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eroo tente novamente");
	    }
	}
	
	
	@DeleteMapping("/conceito/{idConceito}")
	@CrossOrigin
	public ResponseEntity<String> DeletarConceito(@PathVariable Long idConceito) {
		if (cp.existsById(idConceito)){
		    cp.deleteById(idConceito);
		    return ResponseEntity.status(HttpStatus.OK).body("Conceito deletado com sucesso");
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }
	}
	
	@PatchMapping("/conceito/{idConceito}")
	@CrossOrigin
	public ResponseEntity<String> AtualizarConceito(@PathVariable Long idConceito, @RequestBody Map<String, Object> atulizar ) {
		Optional<Conceitos> optionconceito = Optional.ofNullable(cp.findByIdConceito(idConceito));
		
		if (optionconceito.isPresent()) {
			Conceitos cont = optionconceito.get();
			atulizar.forEach((key, value) -> {
			Field field = ReflectionUtils.findRequiredField(Conceitos.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, cont, value);
			});
			
			cp.save(cont);
			return ResponseEntity.status(HttpStatus.OK).body("Conceito atualizado com sucesso");
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conceito não encontrado");
	    }	
	}
	
	
	
	
}
