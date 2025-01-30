package com.example.microserviciofichasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.OPTIONS})
public class NotificacionesController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/user/{id}/subscribe")
    @SendTo("/topic/user/{id}")
    public String subscribeToUser(@DestinationVariable String id) {
        return "Usuario con ID: " + id + " ha sido suscrito al canal de notificaciones.";
    }

    public void sendNotificationToUser(String userId, String message) {
        messagingTemplate.convertAndSendToUser(userId, "/topic/notifications", message);
    }
}
