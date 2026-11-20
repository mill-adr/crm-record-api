package com.example.crm_record;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.crm_record.model.CrmPayload;

@RestController
@RequestMapping("/api/crm-record")

public class CrmRecordController{
    
    private final CrmRecordService crmRecordService;

    public CrmRecordController(CrmRecordService crmRecordService){
        this.crmRecordService = crmRecordService;
    }

    @PostMapping
    public CrmPayload createPayload(@RequestParam String transcriptId){
        
        try{            
            return crmRecordService.crmPayload(transcriptId);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (java.io.FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (java.io.IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed processing review: " + e.getMessage());
        }

    }
}