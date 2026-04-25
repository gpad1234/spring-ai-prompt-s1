package org.springframework.ai.example.prompt_engineering;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prompt")
public class PromptTestController {

	private final ChatClient.Builder chatClientBuilder;

	public PromptTestController(ChatClient.Builder chatClientBuilder) {
		this.chatClientBuilder = chatClientBuilder;
	}

	@PostMapping("/test")
	public ResponseEntity<PromptTestResponse> testPrompt(@RequestBody PromptTestRequest request) {
		if (request == null || !StringUtils.hasText(request.prompt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new PromptTestResponse("", "Prompt is required."));
		}

		double temperature = request.temperature() != null ? request.temperature() : 0.7;
		int maxTokens = request.maxTokens() != null ? request.maxTokens() : 500;

		var spec = chatClientBuilder.build().prompt();
		if (StringUtils.hasText(request.system())) {
			spec = spec.system(request.system());
		}

		String content = spec.user(request.prompt())
			.options(ChatOptions.builder().temperature(temperature).maxTokens(maxTokens))
			.call()
			.content();

		return ResponseEntity.ok(new PromptTestResponse(content, ""));
	}

	record PromptTestRequest(String prompt, String system, Double temperature, Integer maxTokens) {
	}

	record PromptTestResponse(String response, String error) {
	}
}
