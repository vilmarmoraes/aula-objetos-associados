package com.devsuperior.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.PersonDTO;
import com.devsuperior.dscatalog.dto.PersonFullDTO;
import com.devsuperior.dscatalog.entities.Department;
import com.devsuperior.dscatalog.entities.Person;
import com.devsuperior.dscatalog.repositories.DepartmentRepository;
import com.devsuperior.dscatalog.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Transactional(readOnly = true)
	public PersonDTO findById(Long id) {
		Optional<Person> result = repository.findById(id);
		Person entity = result.get();
		return new PersonDTO(entity);
	}

	@Transactional
	public PersonDTO insertA(PersonDTO dto) {
		Person entity = new Person();
		
		entity.setName(dto.getName());
		entity.setSalary(dto.getSalary());
		
		Department dept = new Department();
		dept.setId(dto.getDepartmentId());
		
		entity.setDepartment(dept);
		entity = repository.save(entity);
		
		return new PersonDTO(entity);
	}
	
	@Transactional
	public PersonFullDTO insertB(PersonDTO dto) {
		Person entity = new Person();
		
		entity.setName(dto.getName());
		entity.setSalary(dto.getSalary());
		Department dept = departmentRepository.getOne(dto.getDepartmentId());
		entity.setDepartment(dept);
		entity = repository.save(entity);
		
		return new PersonFullDTO(entity);
	}
}
