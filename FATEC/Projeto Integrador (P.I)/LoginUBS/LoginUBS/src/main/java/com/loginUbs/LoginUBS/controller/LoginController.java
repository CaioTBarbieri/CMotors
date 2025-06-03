package com.LoginUBS.LoginUBS.controller;


import com.LoginUBS.LoginUBS.model.Funcionario;
import com.LoginUBS.LoginUBS.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private FuncionarioRepository ur;

    @GetMapping("/index")
    public String login() {
        return "index";
    }

    @GetMapping("/cadastroFuncionario")
    public String cadastro() {
        return "cadastro";
    }

    @RequestMapping(value = "cadastroFuncionario", method = RequestMethod.POST)
    public String cadastroFuncionario(@Valid Funcionario funcionario, BindingResult result) {

        try {
            ur.save(funcionario);
            return "redirect:/index";
        } catch (Exception e) {
            e.printStackTrace();
            result.reject("erro.salvar", "Erro ao salvar: " + e.getMessage());
            return "cadastro";
        }

    }
}