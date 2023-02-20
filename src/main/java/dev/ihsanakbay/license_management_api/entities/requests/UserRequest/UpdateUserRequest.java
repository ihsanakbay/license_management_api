package dev.ihsanakbay.license_management_api.entities.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<String> roleIds;
}
