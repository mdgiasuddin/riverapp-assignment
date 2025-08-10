package org.example.river.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.river.model.entity.User;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String designation;
    private String company;
    private String address;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.designation = user.getDesignation();
        this.company = user.getCompany();
        this.address = user.getAddress();
    }
}
