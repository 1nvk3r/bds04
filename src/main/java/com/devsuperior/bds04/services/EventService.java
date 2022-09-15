package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;


    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPaginated(Pageable pageable) {
        // Shouldn't be passing entities to the resource anymore
        Page<Event> list = repository.findAll(pageable);

        //Convert list of entity type to DTO type in a stream and convert it back to a list
        return list.map(x -> new EventDTO(x));
    }

    @Transactional
    public EventDTO insert(EventDTO dto) {
        Event entity = new Event();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new EventDTO(entity);
    }
    
    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        try {
            //Using get one prevents acessing the db without necessity
            Event entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            return new EventDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    private void copyDtoToEntity(EventDTO dto, Event entity){
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        City city = cityRepository.getOne(dto.getCityId());
        cityRepository.save(city);
        entity.setCity(city);
    }

}
