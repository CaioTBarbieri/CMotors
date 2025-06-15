package com.CMotors.CMotors.repository;

import com.CMotors.CMotors.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findById(long id);

    @Query(value = "select * from cmotors.usuario where email = :email and senha = :senha", nativeQuery = true)
    public Usuario login(String email, String senha);

    Usuario findByEmail(String email);
}
