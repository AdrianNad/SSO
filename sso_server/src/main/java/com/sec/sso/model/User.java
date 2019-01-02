package com.sec.sso.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String login;
    private String password;
    List<UserRole> authorities = new ArrayList<>();
}
