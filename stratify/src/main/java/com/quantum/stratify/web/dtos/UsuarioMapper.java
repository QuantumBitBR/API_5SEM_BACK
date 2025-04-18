package com.quantum.stratify.web.dtos;

import com.quantum.stratify.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;


public class UsuarioMapper {
    public static Usuario toUsuario(UsuarioCreateDto usuarioCreateDto){
        return new ModelMapper().map(usuarioCreateDto, Usuario.class);
    }
    public static UsuarioResponseDto toDto(Usuario usuario){
        String role = usuario.getRole().name();
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }
    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}

