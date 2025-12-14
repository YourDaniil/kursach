package org.example.Repositories;

import org.example.Models.Enums.UserRoles;
import org.example.Models.Entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByUserRole(UserRoles userRole);

    boolean existsByUserRole(UserRoles userRole);

    @Modifying
    @Transactional
    void deleteByUserRole(UserRoles userRole);
}
