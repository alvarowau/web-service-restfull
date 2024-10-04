package org.alvarowau.webservicerestfull.model.dto.person;

import java.time.LocalDate;


public record PersonSaveDto(
        String firstName,
        String lastName,
        int experience,
        LocalDate birthday
) {
}
