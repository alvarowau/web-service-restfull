package org.alvarowau.webservicerestfull.service.impl;

import org.alvarowau.webservicerestfull.model.dto.person.PersonFullDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;
import org.alvarowau.webservicerestfull.repository.PersonRepository;
import org.alvarowau.webservicerestfull.repository.mysql.PersonRepositoryImplMsql;
import org.alvarowau.webservicerestfull.service.PersonService;
import org.alvarowau.webservicerestfull.validation.PersonValidation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(Connection connection) {
        this.personRepository = new PersonRepositoryImplMsql(connection);
    }

    @Override
    public Optional<PersonFullDto> findById(Long id) {
        try {
            return personRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la persona con ID: " + id, e);
        }
    }

    @Override
    public List<PersonFullDto> findAll() {
        try {
            return personRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todas las personas", e);
        }
    }

    @Override
    public boolean save(PersonSaveDto personSaveDto) {
        try {
            if (!PersonValidation.validate(personSaveDto)) {
                throw new IllegalArgumentException("No se puede guardar la persona, los campos no son correctos");
            }
            return personRepository.save(personSaveDto);
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la persona", e);
        }
    }

    @Override
    public boolean update(PersonUpdateDto personUpdateDto) {
        try {
            // Lógica adicional, como comprobar si el ID existe
            if (personUpdateDto.id() == null) {
                throw new IllegalArgumentException("El ID no puede ser nulo para la actualización");
            }
            return personRepository.update(personUpdateDto);
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la persona", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            // Puedes añadir validaciones o reglas de negocio antes de eliminar
            return personRepository.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar la persona con ID: " + id, e);
        }
    }
}
