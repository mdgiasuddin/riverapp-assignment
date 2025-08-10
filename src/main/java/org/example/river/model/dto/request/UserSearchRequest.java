package org.example.river.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String designation;
    private String company;
    private String address;
}
