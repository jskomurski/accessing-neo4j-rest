package com.skomurski.accessingneo4jrest;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    List<Employee> findByEmployeeName(@Param("name") String name);
}
*/
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {
    List<Employee> findByEmployeeName(@Param("name") String name);
}