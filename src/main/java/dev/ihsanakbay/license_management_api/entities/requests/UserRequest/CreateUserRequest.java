package dev.ihsanakbay.license_management_api.entities.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String email;
    private String fullNane;
    private String password;
    private Set<String> roleIds;
}
