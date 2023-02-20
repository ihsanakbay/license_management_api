package dev.ihsanakbay.license_management_api.service;

import dev.ihsanakbay.license_management_api.entities.requests.TodoRequest.CreateTodoRequest;
import dev.ihsanakbay.license_management_api.entities.requests.TodoRequest.UpdateTodoRequest;
import dev.ihsanakbay.license_management_api.exception.DataNotFoundException;
import dev.ihsanakbay.license_management_api.entities.model.Todo;
import dev.ihsanakbay.license_management_api.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodosByLicenseId(String licenseId) {
        return todoRepository.findByLicenseId(licenseId);
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Todo not found with id: " + id));
    }

    public Todo createTodo(CreateTodoRequest request) {
        Todo todo = new Todo(
                request.todoText(),
                request.isDone(),
                request.deadlineDate(),
                LocalDateTime.now(),
                request.license()
        );
        return todoRepository.save(todo);
    }

    public Todo updateTodo(UpdateTodoRequest request) {
        var founded = getTodoById(request.id());

        Todo todo = new Todo(
                founded.getId(),
                request.todoText(),
                request.isDone(),
                request.deadlineDate(),
                founded.getCreatedAt(),
                LocalDateTime.now(),
                founded.getLicense()
        );

        return todoRepository.save(todo);
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

}
