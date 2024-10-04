package org.alvarowau.webservicerestfull.service;

import org.alvarowau.webservicerestfull.model.dto.person.PersonFullDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonSaveDto;
import org.alvarowau.webservicerestfull.model.dto.person.PersonUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<PersonFullDto> findById(Long id);

    List<PersonFullDto> findAll();

    boolean save(PersonSaveDto personSaveDto);

    boolean update(PersonUpdateDto personUpdateDto);

    boolean delete(Long id);
}
