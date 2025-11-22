package tech.mayanktiwari.common.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private String code;
    private String details;
    private List<FieldErrorDetail> fields;

    @Data
    @Builder
    public static class FieldErrorDetail {
        private String field;
        private String message;
    }
}