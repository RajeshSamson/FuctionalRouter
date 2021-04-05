package com.itguru.routerexample;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.itguru.routerexample.handler.EmployeeHandler;
import com.itguru.routerexample.model.Employee;
import com.itguru.routerexample.repository.EmployeeRepository;
import com.itguru.routerexample.router.EmployeeRouter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@TestInstance(Lifecycle.PER_CLASS)
@WebFluxTest
@ContextConfiguration(classes = {EmployeeRouter.class, EmployeeHandler.class})
public class EmployeeRouterTest {

  @Autowired
  ApplicationContext context;

  WebTestClient webTestClient;

  @MockBean
  EmployeeRepository repository;

  @BeforeAll
  public void setUp() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  public void testCreateEmployee() {
    Employee employee = new Employee("1001", "John", "Doe", "johndoe@yahoo.com");
    when(repository.save(any())).thenReturn(Mono.just(employee));

    webTestClient.post().uri("/employee")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(employee), Employee.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Employee.class)
        .value(response -> {
          Assertions.assertThat(response.getFirstName()).isEqualTo("John");
          Assertions.assertThat(response.getId()).isEqualTo("1001");
        });
  }

  @Test
  public void testGetEmployee() {
    Employee employee = new Employee("123", "Joe", "Steve", "steve@gmail.com");
    Flux<Employee> employees = Flux.just(employee);
    when(this.repository.findAll()).thenReturn(employees);

    webTestClient.get().uri("/employee")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Employee.class)
        .value(response -> {
          Assertions.assertThat(response.get(0).getFirstName()).isEqualTo("Joe");
          Assertions.assertThat(response.get(0).getLastName()).isNotNull();
        });
  }

}
