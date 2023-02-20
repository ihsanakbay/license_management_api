package dev.ihsanakbay.license_management_api.service;


import dev.ihsanakbay.license_management_api.entities.dto.UserDto;
import dev.ihsanakbay.license_management_api.entities.model.Role;
import dev.ihsanakbay.license_management_api.entities.model.User;
import dev.ihsanakbay.license_management_api.entities.requests.UserRequest.CreateUserRequest;
import dev.ihsanakbay.license_management_api.entities.requests.UserRequest.UpdateUserRequest;
import dev.ihsanakbay.license_management_api.entities.responses.ServiceResponse;
import dev.ihsanakbay.license_management_api.exception.ResourceNotFoundException;
import dev.ihsanakbay.license_management_api.mappers.UserMapper;
import dev.ihsanakbay.license_management_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper mapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService, UserMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ServiceResponse<List<UserDto>> findAllUsers() {
        var users = userRepository.findAll()
                .stream()
                .map(mapper::userToUserDto)
                .toList();
        return new ServiceResponse<>(
                true,
                "",
                users
        );
    }

    public ServiceResponse<UserDto> findByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Username : %d is not found", username)));
        return new ServiceResponse<>(
                true,
                "",
                mapper.userToUserDto(user)
        );
    }

    public ServiceResponse<UserDto> findByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User Id : %d is not found", userId)));
        return new ServiceResponse<>(
                true,
                "",
                mapper.userToUserDto(user)
        );
    }

    public ServiceResponse<UserDto> createUser(CreateUserRequest request) {
        String encPwd = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encPwd);

        Set<Role> roles = new HashSet<>();

        for (String roleId : request.getRoleIds()) {
            var founded = roleService.getRoleById(roleId);
            roles.add(founded.getData());
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getFullName(),
                request.getPassword(),
                request.getDistributor(),
                roles
        );

        var result = userRepository.save(user);
        return new ServiceResponse<>(
                true,
                "User created successfully",
                mapper.userToUserDto(result)
        );
    }

    public ServiceResponse<UserDto> updateUser(UpdateUserRequest request, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User Id : %d is not found", id)));

        Set<Role> roles = new HashSet<>();

        for (String roleId : request.getRoleIds()) {
            var founded = roleService.getRoleById(roleId);
            roles.add(founded.getData());
        }

        User updatedUser = new User(
                user.getId(),
                user.getUsername(),
                request.getEmail(),
                request.getFullName(),
                user.getPassword(),
                user.getDistributor(),
                roles
        );

        var result = userRepository.save(updatedUser);
        return new ServiceResponse<>(
                true,
                "User updated",
                mapper.userToUserDto(result)
        );
    }

    public ServiceResponse<String> deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User Id : %d is not found", userId)));
        userRepository.deleteById(user.getId());
        return new ServiceResponse<>(
                true,
                "User deleted"
        );
    }

    public Boolean checkIfUserExist(String username) {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        }
        return true;
    }
}
