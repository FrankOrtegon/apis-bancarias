package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public Usuario guardarUsuario(Usuario usuario){
        String sql = """
                INSERT INTO usuarios(nombre, correo, telefono, edad, saldo)
                VALUES (?, ?, ?, ?, ?)
                RETURNING idUsuario
                """;
        try(
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getCorreo());
            statement.setString(3, usuario.getTelefono());
            statement.setInt(4, usuario.getEdad());
            statement.setDouble(5, usuario.getSaldo());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                usuario.setIdUsuario(resultSet.getString("idUsuario"));
            }

            return usuario;

        }catch (SQLException mensajeError){

            throw new RuntimeException("Error guardando cliente", mensajeError);

        }
    }
}
