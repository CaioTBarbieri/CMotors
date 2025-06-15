package com.CMotors.CMotors.controller;

import com.CMotors.CMotors.model.Usuario;
import com.CMotors.CMotors.repository.UsuarioRepository;
import com.CMotors.CMotors.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
        public String cadastro() {
            return "cadastro";
        }

    @GetMapping("/")
    public String telaInicial(Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("nome",CookieService.getCookieValue(request, "NomeUsuario"));
        return "index";
    }

    @GetMapping("/sair")
    public String sair(HttpServletResponse response) throws Exception {
        CookieService.setCookie(response, "id","", 0);
        return "redirect:/login";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws Exception {
        Usuario f = ur.login(usuario.getEmail(), usuario.getSenha());
        if (f != null) {
            CookieService.setCookie(response,"id", String.valueOf(f.getId()), 3600);
            CookieService.setCookie(response,"NomeUsuario", String.valueOf(f.getNome()), 3600);
            return "redirect:/";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos.");
            return "login";
        }
    }

    @RequestMapping(value = "cadastro", method = RequestMethod.POST)
    public String cadastro(@Valid Usuario usuario, BindingResult result) {

        if (ur.findByEmail(usuario.getEmail()) != null) {
            result.rejectValue("email", "erro.email", "Email já cadastrado.");
            return "cadastro";
        }

        try {
            ur.save(usuario);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            result.reject("erro.salvar", "Erro ao salvar: " + e.getMessage());
            return "cadastro";
        }

    }
    }

