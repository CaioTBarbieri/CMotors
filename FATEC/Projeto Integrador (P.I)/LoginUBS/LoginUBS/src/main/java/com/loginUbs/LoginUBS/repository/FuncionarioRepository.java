package com.LoginUBS.LoginUBS.repository;

import com.LoginUBS.LoginUBS.model.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Funcionario findById(long id);
}
