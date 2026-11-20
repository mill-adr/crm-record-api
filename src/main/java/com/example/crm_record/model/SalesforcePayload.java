package com.example.crm_record.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record SalesforcePayload (
    @JsonProperty("attributes") Attributes attributes,
    @JsonProperty("Status") String status,
    @JsonProperty("Origin") String origin,
    @JsonProperty("Priority") String priority,
    @JsonProperty("Type") String type,
    @JsonProperty("Reason") String reason,
    @JsonProperty("Subject") String subject,
    @JsonProperty("Description") String description,
    @JsonProperty("SuppliedName") String suppliedName,
    @JsonProperty("SuppliedEmail") String suppliedEmail,
    @JsonProperty("IsClosedOnCreate") Boolean isClosedOnCreate,
    @JsonProperty("First_Contact_Resolution__c") Boolean firstContactResolution,
    @JsonProperty("Follow_Up_Required__c") Boolean followUpRequired,
    @JsonProperty("Agent_Name__c") String agentName,
    @JsonProperty("Primary_Categories__c") String primaryCategories,
    @JsonProperty("Issue_Action_Tags__c") String issueActionTags,
    @JsonProperty("Outcome_Sentiment_Tags__c") String outcomeSentimentTags
) implements CrmPayload{
    public record Attributes(
        @JsonProperty("type") String type
    ) {
        public static Attributes forCase() {
            return new Attributes("Case");
        }
    }

    public static SalesforcePayload of(
        String status,
        String origin,
        String priority,
        String type,
        String reason,
        String subject,
        String description,
        String suppliedName,
        String suppliedEmail,
        Boolean isClosedOnCreate,
        Boolean firstContactResolution,
        Boolean followUpRequired,
        String agentName,
        String primaryCategories,
        String issueActionTags,
        String outcomeSentimentTags
    ) {
        return new SalesforcePayload(
            Attributes.forCase(),
            status,
            origin,
            priority,
            type,
            reason,
            subject,
            description,
            suppliedName,
            suppliedEmail,
            isClosedOnCreate,
            firstContactResolution,
            followUpRequired,
            agentName,
            primaryCategories,
            issueActionTags,
            outcomeSentimentTags
        );
    }
};
