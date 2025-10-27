package com.daar.adapter.in.rest.config;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetSocketAddress;

public class RestServer {

    private final Router router;
    private final int port;
    private HttpServer server;

    public RestServer(Router router, int port) {
        this.router = router;
        this.port = port;
    }

    /**
     * Démarre le serveur HTTP
     */
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Toutes les requêtes passent par ce contexte
        server.createContext("/", this::handleRequest);

        server.setExecutor(null); // Exécuteur par défaut
        server.start();
        System.out.println("Server started on port " + port);
    }

    /**
     * Stoppe le serveur
     */
    public void stop() {
        if (server != null) server.stop(0);
    }

    /**
     * Dispatcher central vers le Router
     */
    private void handleRequest(HttpExchange exchange) {
        try {
            router.route(exchange);
        } catch (IOException e) {
            try {
                String response = "{\"error\":\"Internal server error\"}";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

