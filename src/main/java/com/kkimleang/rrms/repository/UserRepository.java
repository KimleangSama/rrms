package com.kkimleang.rrms.repository;

import com.kkimleang.rrms.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByIdIn(List<UUID> ids);

    Optional<User> findByVerifyCode(String verifyCode);

    @Modifying
    @Query("UPDATE User u SET u.profilePicture = :profilePicture WHERE u.id = :id")
    Integer updateAvatar(UUID id, String profilePicture);

    @Modifying
    @Query("UPDATE User u SET u.userStatus = :status WHERE u.id = :id")
    Integer updateAuthStatus(UUID id, String status);
}
