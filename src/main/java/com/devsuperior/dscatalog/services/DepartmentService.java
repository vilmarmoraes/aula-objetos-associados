package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.DepartmentDTO;
import com.devsuperior.dscatalog.entities.Department;
import com.devsuperior.dscatalog.repositories.DepartmentRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	@Transactional(readOnly = true)
	public Page<DepartmentDTO> findAllPaged(Pageable pageable) {
		Page<Department> list = repository.findAll(pageable);
		return list.map(x -> new DepartmentDTO(x));
	}

	@Transactional(readOnly = true)
	public DepartmentDTO findById(Long id) {
		Optional<Department> obj = repository.findById(id);
		Department entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new DepartmentDTO(entity);
	}

	@Transactional
	public DepartmentDTO insert(DepartmentDTO dto) {
		Department entity = new Department();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new DepartmentDTO(entity);
	}

	@Transactional
	public DepartmentDTO update(Long id, DepartmentDTO dto) {
		try {
			Department entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new DepartmentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity vialation");
		}
	}
}
