package org.springframework.ai.mcp.samples.filesystem.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/")
    public String showForm() {
        return "chat"; // chat.html を返す
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String question, Model model) {
        String response = chatClient.prompt(question).call().content();
        model.addAttribute("question", question);
        model.addAttribute("response", response);
        return "chat"; // chat.html に結果を渡す
    }
}
