package org.alvarowau.webservicerestfull.repository.mysql;

public interface PersonQueryMysql {

    String SAVE_PERSON = "INSERT INTO Person (first_name, last_name, experience_years, birth_date) VALUES (?, ?, ?, ?)";
    String UPDATE_PERSON = "UPDATE Person SET first_name = ?, last_name = ? WHERE person_id = ?";
    String DELETE_PERSON = "DELETE FROM Person WHERE person_id = ?";
    String SELECT_PERSON_BY_ID = "SELECT * FROM Person WHERE person_id = ?";
    String SELECT_ALL_PERSON = "SELECT * FROM Person";
}
