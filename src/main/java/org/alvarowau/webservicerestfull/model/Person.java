package org.alvarowau.webservicerestfull.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person extends Base{

    private String firstName;
    private String lastName;
    private int experience;
    private LocalDate birthDate;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, int experience, LocalDate birthDate) {
        super(id);
        setFirstName(firstName);
        setLastName(lastName);
        setExperience(experience);
        setBirthDate(birthDate);
    }

    public Person(String firstName, String lastName, int experience, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setExperience(experience);
        setBirthDate(birthDate);
    }

    public Person(Long id) {
        super(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.split(" ").length < 2) {
            throw new IllegalArgumentException("El apellido debe contener al menos dos palabras.");
        }
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("La experiencia no puede ser negativa.");
        }
        this.experience = experience;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return experience == person.experience && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, experience, birthDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", birthDate=" + birthDate +
                "} " + super.toString();
    }
}
