package com.ovsenka.words.Repository;

import java.util.List;
import java.util.Optional;

import com.ovsenka.words.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByChatId(String id);
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByFirstName(String firstName);

    List<UserEntity> findByLastName(String lastName);

    List<UserEntity> findByPhoneNumber(String phoneNumber);

    List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

    List<UserEntity> findByFirstNameOrLastName(String firstName, String lastName);
}