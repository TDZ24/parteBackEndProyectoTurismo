package com.tuapp.repository;
import com.tuapp.model.Usuario;
import java.util.List;

public interface UsuarioRepository {

    Usuario guardar(Usuario usuario);

    List<Usuario> listar();

    Usuario buscarPorId(Long id);

    Usuario actualizar(Long id, Usuario usuario);

    void eliminar(Long id);
}
