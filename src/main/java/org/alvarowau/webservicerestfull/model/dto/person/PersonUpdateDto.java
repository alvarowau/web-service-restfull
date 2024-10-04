package org.alvarowau.webservicerestfull.model.dto.person;

public record PersonUpdateDto(
        Long id,
        String firstName,
        String lastName
) {
}
