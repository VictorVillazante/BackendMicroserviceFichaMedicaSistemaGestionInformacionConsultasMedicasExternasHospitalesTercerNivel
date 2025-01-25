package com.example.microserviciofichasmedicas.configuration.websockets.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WebSocketPayload {
    private String type;
    private String content;
}
