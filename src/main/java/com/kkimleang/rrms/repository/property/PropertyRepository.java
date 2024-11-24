package com.kkimleang.rrms.repository.property;

import com.kkimleang.rrms.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    boolean existsByUserIdAndName(UUID id, String name);

    List<Property> findByUserId(UUID landlordId);
}
