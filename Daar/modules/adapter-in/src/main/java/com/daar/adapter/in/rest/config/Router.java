package com.daar.adapter.in.rest.config;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Router {

    private final Map<String, Consumer<HttpExchange>> routes = new HashMap<>();
    private final JwtFilter jwtFilter;

    public Router(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    public JwtFilter getJwtFilter() {
        return jwtFilter;
    }

    // Enregistrer une route POST
    public void post(String path, Consumer<HttpExchange> handler) {
        routes.put("POST:" + path, handler);
    }

    // Enregistrer une route GET
    public void get(String path, Consumer<HttpExchange> handler) {
        routes.put("GET:" + path, handler);
    }

    // Dispatcher central
    public void route(HttpExchange exchange) throws IOException {
        String key = exchange.getRequestMethod() + ":" + exchange.getRequestURI().getPath();
        Consumer<HttpExchange> handler = routes.get(key);

        if (handler == null) {
            sendNotFound(exchange);
            return;
        }

        // Vérifier JWT pour toutes les routes (stateless)
        if (!jwtFilter.authorize(exchange)) return;

        // Appeler le handler
        handler.accept(exchange);
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        String response = "{\"error\":\"Not Found\"}";
        exchange.sendResponseHeaders(404, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
