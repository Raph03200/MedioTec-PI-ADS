package com.agendasenac.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agendasenac.modells.UserSistema;
import com.agendasenac.services.RegraUsers;

public interface UserSistemaRepository extends CrudRepository<UserSistema, Long> {
	UserSistema findBycodigo(Long codigo);
	
	Optional<UserSistema> findByimailUser(String imailUser);
	
	@Query("SELECT u FROM UserSistema u WHERE u.imailUser = :imailUser AND u.SenhaAcessoUser = :SenhaAcessoUser")
	Optional<UserSistema> findByImailUserAndSenhaAcessoUser(String imailUser, String SenhaAcessoUser);
	
	
	
}

