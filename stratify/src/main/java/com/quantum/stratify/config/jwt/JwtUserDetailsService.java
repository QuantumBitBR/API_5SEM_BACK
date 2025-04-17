package com.quantum.stratify.config.jwt;


import com.quantum.stratify.entities.Usuario;
import com.quantum.stratify.enums.Role;
import com.quantum.stratify.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String email) {
        Role role = usuarioService.buscarRolePorEmail(email);
        return JwtUtils.createToken(email, role.name().substring("ROLE_".length()));
    }
}
