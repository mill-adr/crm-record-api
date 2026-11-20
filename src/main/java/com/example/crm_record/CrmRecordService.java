package com.example.crm_record;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.crm_record.model.CrmPayload;
import com.example.crm_record.model.SalesforcePayload;
import com.example.crm_record.model.ZendeskPayload;

import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service

public class CrmRecordService{

    @Value("${app.transcripts-dir}")
    private Path transcriptsDir;

    private final ChatClient chatClient;

    public CrmRecordService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    public String loadTranscript(String transcriptId) throws IOException{
        String fileName = transcriptId + ".txt";
        Path filePath = transcriptsDir.resolve(fileName);

        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(
            "No transcript file found for transcriptid " + transcriptId 
            + ". Expected: " + filePath);            
        }

        return Files.readString(filePath);
    }

    public CrmPayload crmPayload(String transcriptId) throws IOException {
        String crm = transcriptId.toLowerCase().split("_")[0];
        String transcript = this.loadTranscript(transcriptId);

        return switch (crm) {
            case "salesforce" -> entityPayload(SalesforcePayload.class, crm, transcript);
            case "zendesk" -> entityPayload(ZendeskPayload.class, crm, transcript);
            default -> throw new IllegalArgumentException("Unsupported CRM: " + crm);
        };
    }

    private <T extends CrmPayload> T entityPayload(Class<T> type, String crm, String transcript) {
        
        return chatClient.prompt()
            .user("Analyze this call transcript and create a " + crm + " payload. Transcript: " + transcript)
            .call()
            .entity(type);
    }

}