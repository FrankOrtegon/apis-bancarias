package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.example.controller.UsuarioController;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioController usuarioController  = new UsuarioController(usuarioService, objectMapper);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/usuarios", usuarioController::handle);

        server.setExecutor(Executors.newFixedThreadPool(10));

        server.start();
        System.out.println("Servidor escuchando en el puerto 8080");

    }
}