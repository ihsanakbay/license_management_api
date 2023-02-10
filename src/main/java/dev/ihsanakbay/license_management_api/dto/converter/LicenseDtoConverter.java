package dev.ihsanakbay.license_management_api.dto.converter;

import dev.ihsanakbay.license_management_api.dto.CountryDto;
import dev.ihsanakbay.license_management_api.dto.LicenseDto;
import dev.ihsanakbay.license_management_api.dto.TodoDto;
import dev.ihsanakbay.license_management_api.model.Country;
import dev.ihsanakbay.license_management_api.model.License;
import dev.ihsanakbay.license_management_api.model.Todo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LicenseDtoConverter {

    public LicenseDto convert(License from) {
        return new LicenseDto(
                from.getId(),
                from.getProductName(),
                from.getNumber(),
                from.getLicenseType(),
                from.getOwner(),
                from.getDistributor(),
                from.getStatus(),
                from.getClosureDate(),
                from.getValidityDate(),
                from.getDescription(),
                from.getStorageCondition(),
                from.getShelfLife(),
                from.getBatchSize(),
                from.getApiSupplier(),
                from.getPackingInfo(),
                from.getTag(),
                from.getCreatedAt(),
                from.getUpdatedAt(),
                getCountry(from.getCountry()),
                getTodos(Optional.ofNullable(from.getTodos()))
        );
    }

    private List<TodoDto> getTodos(Optional<List<Todo>> todos) {
        List<Todo> newTodos = new ArrayList<>();
        if (!todos.isEmpty()) {
            newTodos.addAll(todos.get());
            return newTodos.stream()
                    .map(t -> new TodoDto(
                            t.getId(),
                            t.getTodoText(),
                            t.getIsDone(),
                            t.getDeadlineDate()
                    )).toList();
        }
        return new ArrayList<>();
    }

    private CountryDto getCountry(Country country) {
        return new CountryDto(
                country.getId(),
                country.getCode(),
                country.getName(),
                country.getStatus(),
                country.getCreatedAt(),
                country.getUpdatedAt()
        );
    }
}
