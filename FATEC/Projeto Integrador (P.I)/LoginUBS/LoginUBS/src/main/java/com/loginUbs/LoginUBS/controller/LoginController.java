package com.LoginUBS.LoginUBS.controller;


import com.LoginUBS.LoginUBS.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.LoginUBS.LoginUBS.model.Funcionario;
import com.LoginUBS.LoginUBS.repository.FuncionarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/")
    public String telaInicial(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("nome",CookieService.getCookieValue(request, "NomeFuncionario"));
        return "main";
    }

    @PostMapping("/logar")
    public String loginFuncionario(Funcionario funcionario, Model model, HttpServletResponse response) throws Exception {
        Funcionario f = ur.login(funcionario.getEmail(), funcionario.getSenha());
        if (f != null) {
             CookieService.setCookie(response,"id", String.valueOf(f.getId()), 3600);
            CookieService.setCookie(response,"NomeFuncionario", String.valueOf(f.getNome()), 3600);
            return "redirect:/";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            return "index";
        }
    }

    @GetMapping("/sair")
    public String sair(HttpServletResponse response) throws Exception {
          CookieService.setCookie(response, "id","", 0);
          return "redirect:/index";
        }

    @GetMapping("/cadastroFuncionario")
    public String cadastro() {
        return "cadastro";
    }

    private boolean isCpfValido(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;
        int d1 = 0, d2 = 0;
        for (int i = 0; i < 9; i++) {
            d1 += (cpf.charAt(i) - '0') * (10 - i);
            d2 += (cpf.charAt(i) - '0') * (11 - i);
        }
        d1 = 11 - (d1 % 11);
        if (d1 >= 10) d1 = 0;
        d2 += d1 * 2;
        d2 = 11 - (d2 % 11);
        if (d2 >= 10) d2 = 0;
        return d1 == (cpf.charAt(9) - '0') && d2 == (cpf.charAt(10) - '0');
    }

    @RequestMapping(value = "cadastroFuncionario", method = RequestMethod.POST)
    public String cadastroFuncionario(@Valid Funcionario funcionario, BindingResult result) {
        if (!isCpfValido(funcionario.getCpf())) {
            result.rejectValue("cpf", "erro.cpf", "CPF inválido.");
            return "cadastro";
        }

        if (ur.findByCpf(funcionario.getCpf()) != null) {
            result.rejectValue("cpf", "erro.cpf", "CPF já cadastrado.");
            return "cadastro";
        }
        if (ur.findByEmail(funcionario.getEmail()) != null) {
            result.rejectValue("email", "erro.email", "Email já cadastrado.");
            return "cadastro";
        }

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