package com.fdmgroup.backend_eventhub.eventsession.repository;

import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByEvent(Event event);
}
