package org.example.river.repository;

import org.example.river.model.entity.FriendRequest;
import org.example.river.model.enumeration.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("select f from FriendRequest f join f.sender s join f.receiver r " +
            "where (s.id = :senderId and r.id = :receiverId) or " +
            "(s.id = :receiverId and r.id = :receiverId)")
    Optional<FriendRequest> getFriendRequest(long senderId, long receiverId);

    @Query("select f from FriendRequest f join f.sender s join f.receiver r " +
            "where f.status = :status and (s.id = :userId or r.id = :userId)")
    List<FriendRequest> getFriendRequest(long userId, FriendRequestStatus status);
}
