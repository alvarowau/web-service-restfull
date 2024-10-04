package org.alvarowau.webservicerestfull.repository;

import org.alvarowau.webservicerestfull.model.dto.person.PersonFullDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Optional<PersonFullDto> findById(Long id) throws SQLException;
    List<PersonFullDto> findAll() throws SQLException;
    boolean save(PersonSaveDto personSaveDto) throws SQLException;
    boolean update(PersonUpdateDto personUpdateDto) throws SQLException;
    boolean delete(Long id) throws SQLException;
}
