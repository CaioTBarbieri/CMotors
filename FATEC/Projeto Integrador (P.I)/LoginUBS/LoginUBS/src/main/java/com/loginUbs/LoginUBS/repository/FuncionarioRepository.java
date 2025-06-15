package com.LoginUBS.LoginUBS.repository;

import com.LoginUBS.LoginUBS.model.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Funcionario findById(long id);
    Funcionario findByCpf(String cpf);

    @Query(value = "select * from loginubs.funcionario where email = :email and senha = :senha", nativeQuery = true)
    public Funcionario login(String email, String senha);

    Funcionario findByEmail(String email);
}
