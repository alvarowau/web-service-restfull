package org.alvarowau.webservicerestfull.repository.mysql;

import org.alvarowau.webservicerestfull.model.dto.person.PersonFullDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;
import org.alvarowau.webservicerestfull.repository.PersonRepository;
import org.alvarowau.webservicerestfull.repository.exception.PersonRepositoryException;
import org.alvarowau.webservicerestfull.util.UtilPerson;
import org.alvarowau.webservicerestfull.validation.PersonValidation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepositoryImplMsql implements PersonRepository {

    private final Connection connection;

    public PersonRepositoryImplMsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<PersonFullDto> findById(Long id) {
        try (PreparedStatement stmt = connection.prepareStatement(PersonQueryMysql.SELECT_PERSON_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToPerson(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersonRepositoryException("Error al buscar la persona con id: " + id, e);
        }
    }

    @Override
    public List<PersonFullDto> findAll() {
        List<PersonFullDto> persons = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(PersonQueryMysql.SELECT_ALL_PERSON);
            while (set.next()) {
                persons.add(mapResultSetToPerson(set));
            }
        } catch (SQLException e) {
            throw new PersonRepositoryException("Error al listar las personas", e);
        }
        return persons;
    }

    @Override
    public boolean save(PersonSaveDto personSaveDto) {

        try (PreparedStatement statement = connection.prepareStatement(PersonQueryMysql.SAVE_PERSON)) {
            statement.setString(1, personSaveDto.firstName());
            statement.setString(2, personSaveDto.lastName());
            statement.setInt(3, personSaveDto.experience());
            statement.setDate(4, UtilPerson.convertLocalDateToSqlDate(personSaveDto.birthday()));

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new PersonRepositoryException("Error al guardar la persona: " + personSaveDto, e);
        }
    }



    @Override
    public boolean update(PersonUpdateDto personUpdateDto) {
        try (PreparedStatement statement = connection.prepareStatement(PersonQueryMysql.UPDATE_PERSON)) {
            statement.setString(1, personUpdateDto.firstName());
            statement.setString(2, personUpdateDto.lastName());
            statement.setLong(3, personUpdateDto.id());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new PersonRepositoryException("Error al actualizar la persona: " + personUpdateDto, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(PersonQueryMysql.DELETE_PERSON)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new PersonRepositoryException("No se encontró ninguna persona con el id: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new PersonRepositoryException("No se pudo borrar la persona con el id: " + id, e);
        }
    }

    private PersonFullDto mapResultSetToPerson(ResultSet resultSet) {
        try {
            LocalDate birthDate = UtilPerson.convertSqlDateToLocalDate(resultSet.getDate("birth_date"));
            if (birthDate == null) {
                throw new PersonRepositoryException("La fecha de nacimiento no puede ser nula para el registro con ID: " + resultSet.getLong("person_id"));
            }

            String firstName = resultSet.getString("first_name");
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new PersonRepositoryException("El nombre no puede ser nulo o vacío para el registro con ID: " + resultSet.getLong("person_id"));
            }

            String lastName = resultSet.getString("last_name");
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new PersonRepositoryException("El apellido no puede ser nulo o vacío para el registro con ID: " + resultSet.getLong("person_id"));
            }

            return new PersonFullDto(
                    resultSet.getLong("person_id"),
                    firstName,
                    lastName,
                    resultSet.getInt("experience_years"),
                    birthDate
            );
        } catch (SQLException e) {
            throw new PersonRepositoryException("Error al mapear el ResultSet a PersonFullDto", e);
        }
    }
}
