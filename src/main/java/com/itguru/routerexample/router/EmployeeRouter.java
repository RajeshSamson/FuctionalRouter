package com.itguru.routerexample.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.itguru.routerexample.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EmployeeRouter {

  @Bean
  public RouterFunction<ServerResponse> router(EmployeeHandler handler) {
    return RouterFunctions
        .route(GET("/employee")
                .and(accept(APPLICATION_JSON))
            , handler::getAllEmployees)
        .andRoute(POST("/employee")
                .and(accept(APPLICATION_JSON))
            , handler::createEmployee);
  }

}
