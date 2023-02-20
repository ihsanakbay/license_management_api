package dev.ihsanakbay.license_management_api.controller;

import dev.ihsanakbay.license_management_api.entities.dto.UserDto;
import dev.ihsanakbay.license_management_api.entities.requests.UserRequest.CreateUserRequest;
import dev.ihsanakbay.license_management_api.entities.requests.UserRequest.UpdateUserRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    ServiceResponse<List<UserDto>> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ServiceResponse<UserDto> findByUserId(@PathVariable("userId") String id) {
        return userService.findByUserId(id);
    }

    @PostMapping() //ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    public ServiceResponse<UserDto> createUser(@RequestBody @Valid CreateUserRequest user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ServiceResponse<UserDto> updateUser(@RequestBody @Valid UpdateUserRequest request, @PathVariable("id") String id) {
        return userService.updateUser(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ServiceResponse<String> deleteUserById(@PathVariable String id) {
        return userService.deleteUserById(id);
    }
}
