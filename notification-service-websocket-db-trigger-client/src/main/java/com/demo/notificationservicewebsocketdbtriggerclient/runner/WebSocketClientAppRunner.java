package com.demo.notificationservicewebsocketdbtriggerclient.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketClientAppRunner implements CommandLineRunner {

    @Override

    public void run(String... args) throws Exception {
        connectToWebSocketServer();
    }

    public void connectToWebSocketServer() throws Exception {
        // Create SockJS client to use with WebSocket
        SockJsClient sockJsClient = new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))
        );
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        // Optional: Heartbeat/reconnection logic (ThreadPoolTaskScheduler)
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        stompClient.setTaskScheduler(scheduler);

        // Define the session handler
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("[CLIENT] Connected to WebSocket Server");

                // Subscribe to the topic where messages are broadcasted
                session.subscribe("/topic/notification", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class; // Expected payload type is String
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println("[CLIENT] Received: " + payload.toString());
                    }
                });

                // Send a test message to the server
                session.send("/app/notification", "Hello from Spring Boot client!");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.err.println("[CLIENT] Transport Error: " + exception.getMessage());
            }

        };

        // Connect to the WebSocket server
        String serverUrl = "ws://localhost:8080/ws";  // Ensure this matches the server endpoint
        CompletableFuture<StompSession> stompSessionCompletableFuture = stompClient.connectAsync(serverUrl, sessionHandler);

        // Wait for the connection to complete and run for a while
        StompSession session = stompSessionCompletableFuture.get();
        System.out.println("[CLIENT] WebSocket connection established, running client...");

        // Keep the client alive for demo (optional, can be adjusted as needed)
        TimeUnit.MINUTES.sleep(10);

        // Gracefully disconnect after demo period
        session.disconnect();
        System.out.println("[CLIENT] Disconnected from WebSocket Server");
    }
}
