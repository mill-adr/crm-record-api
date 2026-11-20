package com.example.crm_record.model;


public sealed interface CrmPayload permits SalesforcePayload, ZendeskPayload {}
