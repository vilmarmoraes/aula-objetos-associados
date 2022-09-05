package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscatalog.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
