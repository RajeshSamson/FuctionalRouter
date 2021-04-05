package com.itguru.routerexample.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import com.itguru.routerexample.model.Employee;
import com.itguru.routerexample.repository.EmployeeRepository;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeHandler {

  private final EmployeeRepository repository;

  public EmployeeHandler(EmployeeRepository repository) {
    this.repository = repository;
  }

  public Mono<ServerResponse> createEmployee(ServerRequest request){
    Mono<Employee> employee = request.bodyToMono(Employee.class);
    Mono<Employee> publisher = employee.flatMap(repository::save);
    var inserter = fromPublisher(publisher, Employee.class);
    return ServerResponse.ok().body(inserter);
  }

  public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
    Flux<Employee> employees = repository.findAll();
    return ServerResponse.ok().body(employees, Employee.class);
  }

  private Mono<ServerResponse> defaultResponse(Publisher<Employee> employee) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(employee, Employee.class);
  }

  public Mono<ServerResponse> createEmployee2(ServerRequest request) {
    Flux<Employee> employeeMono = request.bodyToFlux(Employee.class)
        .flatMap(f -> repository.save(f));
    return defaultResponse(employeeMono);
  }


}
