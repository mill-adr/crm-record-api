package com.example.crm_record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.crm_record.CrmRecordController;
import com.example.crm_record.CrmRecordService;

import java.io.FileNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CrmRecordControllerTest {

    private CrmRecordService crmRecordService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        crmRecordService = Mockito.mock(CrmRecordService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CrmRecordController(crmRecordService)).build();
    }

    @Test
    void returns404WhenTranscriptFileNotFound() throws Exception {
        when(crmRecordService.crmPayload(anyString()))
                .thenThrow(new FileNotFoundException("No transcript file found for transcriptid salesforce_23456. Expected: transcripts_examples/salesforce_23456.txt"));

        mockMvc.perform(post("/api/crm-record?transcriptId=salesforce_23456"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returns400ForUnknownCrm() throws Exception {
        when(crmRecordService.crmPayload("abc_23456"))
                .thenThrow(new IllegalArgumentException("Unsupported CRM: abc"));

        mockMvc.perform(post("/api/crm-record?transcriptId=abc_23456"))
                .andExpect(status().isBadRequest());
    }
}