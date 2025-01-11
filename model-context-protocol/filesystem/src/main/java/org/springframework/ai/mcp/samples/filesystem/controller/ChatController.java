package org.springframework.ai.mcp.samples.filesystem.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    private final ChatClient chatClient;
    private final List<McpFunctionCallback> functionCallbacks;

    public ChatController(ChatClient chatClient, List<McpFunctionCallback> functionCallbacks) {
        this.chatClient = chatClient;
        this.functionCallbacks = functionCallbacks;
    }

    @GetMapping("/read-file")
    @ResponseBody  // レスポンスをテンプレートではなくプレーンテキストとして扱う
    public String readFile(@RequestParam String filePath) {
        try {
            // AIにファイルの内容を読ませる質問を作成
            // String question = "Can you explain the content of the file at path: " + filePath + "?";

            // AIの応答を取得
            String response = chatClient.prompt(filePath).call().content();

            return "File Content:\n" + response;
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    @GetMapping("/summarize-file")
    public String summarizeFile(@RequestParam String filePath) {
        try {
            // AIに要約を求める質問を作成
            String question = "Summarize the content of the file at path: " + filePath;

            // AIの応答を取得
            String response = chatClient.prompt(question).call().content();

            return "File Summary:\n" + response;
        } catch (Exception e) {
            return "Error summarizing file: " + e.getMessage();
        }
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
