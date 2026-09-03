package com.esolutions.travel.travel;

import com.esolutions.travel.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    Page<Travel> findAllByOwner(AppUser owner, Pageable pageable);
    java.util.Optional<Travel> findByIdAndOwner(Long id, AppUser owner);
    boolean existsByIdAndOwner(Long id, AppUser owner);
    void deleteByIdAndOwner(Long id, AppUser owner);
}
