package com.example.microserviciofichasmedicas.configuration.websockets.handlers;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.microserviciofichasmedicas.configuration.websockets.payload.WebSocketPayload;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;


@Component
public class NotificacionesWebSocketHandler  extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Conexión WebSocket establecida con: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Mensaje recibido: " + message.getPayload());
        session.sendMessage(new TextMessage("Mensaje recibido: " + message.getPayload()));
    }
    private void sendMessage(WebSocketSession session) throws Exception {
        System.out.println("Mensaje enviado");
        WebSocketPayload payload=new WebSocketPayload("notificaciones","actualizar");
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("Conexión cerrada con: " + session.getId());
    }
}