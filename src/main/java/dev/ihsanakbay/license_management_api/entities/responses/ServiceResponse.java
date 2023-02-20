package dev.ihsanakbay.license_management_api.entities.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class ServiceResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    public ServiceResponse() {
    }

    public ServiceResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
