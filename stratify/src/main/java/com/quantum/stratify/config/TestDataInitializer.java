package com.quantum.stratify.config;

import com.quantum.stratify.entities.Projeto;
import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.services.ProjetoService;
import com.quantum.stratify.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestDataInitializer {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProjetoService projetoService;

    @PostConstruct
    public void init() {
        try {
            // Cria usuário admin
            criarUsuarioAdmin();
            
            // Cria usuário comum
            criarUsuarioComum();
            
            // Cria projetos de teste
            criarProjetosTeste();
            
        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados de teste: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void criarUsuarioAdmin() {
        Usuario admin = new Usuario();
        admin.setEmail("admin@example.com");
        admin.setNome("Administrador");
        admin.setSenha("senha123");
        admin.setRole(Role.ADMIN);
        admin.setIsEnable(true);
        usuarioService.salvar(admin);
    }

    private void criarUsuarioComum() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@example.com");
        usuario.setNome("Usuário Teste");
        usuario.setSenha("senha123");
        usuario.setRole(Role.USER);
        usuario.setIsEnable(true);
        usuarioService.salvar(usuario);
    }

    private void criarProjetosTeste() {
        Projeto projeto1 = new Projeto();
        projeto1.setNome("Projeto Principal");
        projetoService.salvar(projeto1);

        Projeto projeto2 = new Projeto();
        projeto2.setNome("Projeto Secundário");
        projetoService.salvar(projeto2);
    }
}