package dev.ihsanakbay.license_management_api.mappers;

import dev.ihsanakbay.license_management_api.entities.dto.UserDto;
import dev.ihsanakbay.license_management_api.entities.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto dto);

    UserDto userToUserDto(User user);
}
