package com.itguru.routerexample.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.itguru.routerexample.handler.EmployeeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
            , handler::createEmployee2);
  }

  /*@Bean
  public RouterFunction<ServerResponse> router2(EmployeeHandler handler) {
    return RouterFunctions.route()
        .path("/api/v1", builder -> {
          builder.GET("/employee",
              accept(APPLICATION_JSON),
              handler::getAllEmployees)
              .POST("/employee", accept(APPLICATION_JSON),
                  handler::createEmployee2);
        })
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> router3(EmployeeHandler handler) {
    return RouterFunctions.route()
        .path("/api/v1", builder -> {
          builder.nest(accept(APPLICATION_JSON), jsonBuilder -> {
            jsonBuilder.GET("/employee",
                accept(APPLICATION_JSON),
                handler::getAllEmployees);

          })
              .nest(accept(MediaType.TEXT_HTML),
                  pdfBuilder -> {
                    pdfBuilder.GET("/employee",
                        accept(APPLICATION_JSON),
                        handler::getAllEmployees);
                  });

        })
        .build();
  }*/


}


