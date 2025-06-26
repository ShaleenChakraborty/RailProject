package com.railways.entryportal.repository;

import com.railways.entryportal.model.Datasheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSheetRepository extends JpaRepository<Datasheet, Long> {
    // You can add custom query methods here later if needed
}
