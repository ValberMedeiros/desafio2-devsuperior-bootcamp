package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.exceptions.DataBaseException;
import com.devsuperior.bds02.exceptions.EntityNotFoundException;
import com.devsuperior.bds02.repositories.CityRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<CityDTO> findAll() {
        List<City> entities = repository.findAll(Sort.by("name"));
        return entities.stream()
                    .map(CityDTO::new)
                    .collect(Collectors.toList());
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("City with id %d not found!", id));
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new DataBaseException(String.format("the city with id %d cannot be deleted as it is in use", id));
        }
    }

}
