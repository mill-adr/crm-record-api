package com.example.crm_record.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ZendeskPayload(
    @JsonProperty("ticket") Ticket ticket
) implements CrmPayload {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Ticket(
        @JsonProperty("subject") String subject,
        @JsonProperty("comment") Comment comment,
        @JsonProperty("requester") Requester requester,
        @JsonProperty("status") String status,       
        @JsonProperty("priority") String priority,
        @JsonProperty("type") String type,
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("custom_fields") List<CustomField> customFields
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Comment(
        @JsonProperty("body") String body,
        @JsonProperty("public") Boolean isPublic
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Requester(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email
    ) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CustomField(
        @JsonProperty("id") Long id,
        @JsonProperty("value") Object value
    ) {}
};
