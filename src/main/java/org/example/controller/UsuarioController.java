package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioController implements HttpHandler {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String metodoHttp = exchange.getRequestMethod();
        String ruta = exchange.getRequestURI().getPath();

        try {
            if (metodoHttp.equals("POST") && ruta.equals("/usuarios/crear")) {
                Usuario usuario = objectMapper.readValue(exchange.getRequestBody(), Usuario.class);
                Usuario creado = usuarioService.crearUsuario(usuario);
                responder(exchange, 201, objectMapper.writeValueAsString(creado));
            } else {
                responder(exchange, 404, "{\"error\":\"Ruta no encontrada\"}");
            }
        } catch (IllegalArgumentException e) {
            responder(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            responder(exchange, 500, "{\"error\":\"Error interno del servidor\"}");
        }
    }

    private void responder(HttpExchange exchange, int codigo, String cuerpo) throws IOException {
        byte[] bytes = cuerpo.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(codigo, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}