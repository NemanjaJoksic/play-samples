package org.joksin.playsamples.database.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String address;
    private String city;

}
