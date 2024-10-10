package com.agendasenac.controllers;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.agendasenac.modells.UserSistema;
import com.agendasenac.repository.UserSistemaRepository;

@RestController
public class UserSistemaController{
	
	@Autowired
	private UserSistemaRepository usp;
	

	
	@Autowired
	private PasswordEncoder passwordEncoder;

	

	@GetMapping("/user")
	@CrossOrigin
	public Iterable<UserSistema> UserSistema() {
		return usp.findAll();
	}
	
	
	//teste da paramentro asyng nas requsuições
	
	@GetMapping("/user/{codigo}")
	@CrossOrigin
	public ResponseEntity<UserSistema> getUserByCodigo(@PathVariable Long codigo) {
		
	    Optional<UserSistema> user = Optional.ofNullable(usp.findBycodigo(codigo));

	    if (user.isPresent()) {
	        return ResponseEntity.ok(user.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	}
	//fim do teste
	
	
	
	
	
	@PostMapping("/register")
	@CrossOrigin
	public ResponseEntity<String> userCadastro(@RequestBody UserSistema usersistema) {
	        // Codificar a senha antes de salvar
	        usersistema.setSenhaAcessoUser(passwordEncoder.encode(usersistema.getSenhaAcessoUser()));
	        usp.save(usersistema);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
	}
	
	
	
	
	@DeleteMapping("/user/{codigo}")
	@CrossOrigin
	public ResponseEntity<String> userDelet(@PathVariable Long codigo) {
	    if (usp.existsById(codigo)) {
	        usp.deleteById(codigo);
	        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }
	}

	@PatchMapping("/user/{codigo}")
	@CrossOrigin
	public ResponseEntity<String> updateUser(@PathVariable Long codigo, @RequestBody Map<String, Object> updates) {
	        Optional<UserSistema> optionalUser = Optional.ofNullable(usp.findBycodigo(codigo));

	        if (optionalUser.isPresent()) {
	            UserSistema user = optionalUser.get();

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
	                            ReflectionUtils.setField(field, user, nestedInstance);
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
	                            ReflectionUtils.setField(field, user, value);
	                        } else {
	                            throw new NoSuchFieldException("Campo não encontrado: " + key);
	                        }
	                    }
	                } catch (Exception e) {
	                    throw new RuntimeException("Erro ao atualizar o campo: " + key, e);
	                }
	            });

	            usp.save(user);
	            return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	        }
	}


	
	
	
	
}
