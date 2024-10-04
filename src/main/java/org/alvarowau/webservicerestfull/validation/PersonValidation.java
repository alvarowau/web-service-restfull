package org.alvarowau.webservicerestfull.validation;

import org.alvarowau.webservicerestfull.generalexception.GeneralException;
import org.alvarowau.webservicerestfull.model.Person;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;

import java.time.LocalDate;

public class PersonValidation {

    private PersonValidation() {
        throw new GeneralException("Esta es una clase de utilidades y no debe ser instanciada.");
    }

    public static boolean validate(Person person) {
        return isValidName(person.getFirstName()) &&
                isValidFullName(person.getLastName()) &&
                isExperienceValid(person.getExperience()) &&
                isBirthDateValid(person.getBirthDate());
    }

    public static boolean validate(PersonSaveDto personSaveDto) {
        return isValidName(personSaveDto.firstName()) &&
                isValidFullName(personSaveDto.lastName()) &&
                isExperienceValid(personSaveDto.experience()) &&
                isBirthDateValid(personSaveDto.birthday());
    }

    public static boolean validate(PersonUpdateDto personUpdateDto) {
        return isValidName(personUpdateDto.firstName()) && isValidFullName(personUpdateDto.lastName());
    }

    public static String validateAndErrors(PersonSaveDto personSaveDto) {
        StringBuilder errors = new StringBuilder();
        if (!isValidName(personSaveDto.firstName())) {
            errors.append("El nombre de la persona no es válido.\n");
        }
        if (!isValidFullName(personSaveDto.lastName())) {
            errors.append("El apellido de la persona no es válido.\n");
        }
        if (!isExperienceValid(personSaveDto.experience())) {
            errors.append("La experiencia de la persona no es válida.\n");
        }
        if (!isBirthDateValid(personSaveDto.birthday())) {
            errors.append("La fecha de nacimiento de la persona no es válida.\n");
        }
        return errors.toString();
    }

    private static boolean isValidName(String name) {
        return name != null && name.length() > 3 && name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    private static boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        String[] names = fullName.trim().split(" ");
        return names.length == 2 && names[0].length() > 3 && names[1].length() > 3 &&
                names[0].matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+") && names[1].matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    private static boolean isExperienceValid(int experience) {
        return experience >= 0; // Considerar límites específicos si es necesario
    }

    public static boolean isBirthDateValid(LocalDate birthDate) {
        return birthDate != null && birthDate.isBefore(LocalDate.now());
    }
}
