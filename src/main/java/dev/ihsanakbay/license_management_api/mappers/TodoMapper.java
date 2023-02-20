package dev.ihsanakbay.license_management_api.mappers;

import dev.ihsanakbay.license_management_api.entities.dto.TodoDto;
import dev.ihsanakbay.license_management_api.entities.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo todoDtoToTodo(TodoDto dto);
    TodoDto todoToTodoDto(Todo todo);
}
