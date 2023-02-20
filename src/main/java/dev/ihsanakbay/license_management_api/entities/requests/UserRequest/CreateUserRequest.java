package dev.ihsanakbay.license_management_api.entities.requests.UserRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String distributor;
    private Set<String> roleIds;
}
