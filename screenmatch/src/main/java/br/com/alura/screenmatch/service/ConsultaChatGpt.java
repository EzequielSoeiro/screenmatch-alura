package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGpt {

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService("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");

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}







