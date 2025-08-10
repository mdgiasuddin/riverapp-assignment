package org.example.river.repository;

import org.example.river.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u " +
            "where (:email is null or u.email like :email) " +
            "and (:firstName is null or u.firstName like :firstName) " +
            "and (:lastName is null or u.lastName like :lastName) " +
            "and (:phoneNumber is null or u.phoneNumber like :phoneNumber) " +
            "and (:designation is null or u.designation like :designation) " +
            "and (:company is null or u.company like :company) " +
            "and (:address is null or u.address like :address) "
    )
    List<User> searchUser(String email,
                          String firstName,
                          String lastName,
                          String phoneNumber,
                          String designation,
                          String company,
                          String address
    );
}
