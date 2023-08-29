package com.application.main.features.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from user_pacificollege u where (current_date - u.ultimo_episodio_b) >= 30 and u.estado = 'ACTIVO'",
    countQuery = "select count(*) from user_pacificollege u where (current_date - u.ultimo_episodio_b) >= 30 and u.estado = 'ACTIVO'", nativeQuery = true)
    Page<User> findByDifferentDate(Pageable pageable);
}
