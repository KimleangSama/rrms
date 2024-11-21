package com.kkimleang.rrms.repository.user;

import com.kkimleang.rrms.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<UUID> ids);

    Optional<User> findByVerifyCode(String verifyCode);

    @Modifying
    @Query("UPDATE User u SET u.userStatus = :status, u.verified = :verified WHERE u.id = :id")
    Integer updateVerifyAndAuthStatus(UUID id, String status, Boolean verified);
}
