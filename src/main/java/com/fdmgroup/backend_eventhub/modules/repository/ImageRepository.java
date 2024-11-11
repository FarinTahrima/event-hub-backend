package com.fdmgroup.backend_eventhub.modules.repository;

import com.fdmgroup.backend_eventhub.modules.model.ImageModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModule, Long> {
}
