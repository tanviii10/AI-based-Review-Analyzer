package com.spring.AiReviewAnalysis.responces;

public class AIResult {
	
	private String prompt;   
    private String response; 

    public AIResult() {}

    public AIResult(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
