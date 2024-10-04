package org.alvarowau.webservicerestfull.model.dto.person;

import java.time.LocalDate;


public record PersonFullDto(
        Long id,
        String firstName,
        String lastName,
        int experience,
        LocalDate birthDate
) {
}
