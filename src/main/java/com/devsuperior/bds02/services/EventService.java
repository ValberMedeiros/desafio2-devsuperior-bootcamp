package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    private EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event findById (Long id) {
        Optional<Event> entity = repository.findById(id);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %d, cannot be found.", id));
        }
        return entity.get();

    }

    @Transactional
    public EventDTO update(EventDTO dto, Long id) {
        Event entity = findById(id);
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(new City(dto.getCityId(), null));
        repository.save(entity);

        return new EventDTO(entity);
    }
}
