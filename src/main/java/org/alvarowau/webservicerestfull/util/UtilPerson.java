package org.alvarowau.webservicerestfull.util;


import org.alvarowau.webservicerestfull.generalexception.GeneralException;

import java.time.LocalDate;

public class UtilPerson {

    private UtilPerson() {
        throw new GeneralException("No se puede instanciar esta clase.");
    }
    public static java.time.LocalDate convertSqlDateToLocalDate(java.sql.Date date) {
        return date != null ? date.toLocalDate() : null;
    }

    public static java.sql.Date convertLocalDateToSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }


    public static java.time.LocalDate convertStringToLocalDate(String str) {
        return str != null ? java.time.LocalDate.parse(str) : null;
    }
}
