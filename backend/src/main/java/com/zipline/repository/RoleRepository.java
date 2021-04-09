package com.zipline.repository;

import com.zipline.model.Role;
import com.zipline.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The role repository contains methods for roles entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find a role by its name.
     *
     * @param name the name of the role
     * @return the optional role
     */
    Role findByName(final RoleType name);
}
