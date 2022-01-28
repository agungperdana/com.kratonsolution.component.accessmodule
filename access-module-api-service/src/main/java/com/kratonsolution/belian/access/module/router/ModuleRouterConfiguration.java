package com.kratonsolution.belian.access.module.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;

@Configuration
public class ModuleRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> getAllModules(ModuleHandler moduleHandler) {

        return RouterFunctions
                .route(GET("/api/v2/access-module/all"), moduleHandler::getAll)
                .andRoute(GET("/api/v2/access-module/all-paged/{offset}/{limit}"), moduleHandler::getAllPaged)
                .andRoute(GET("/api/v2/access-module/all-filtered/{key}"), moduleHandler::filterAll)
                .andRoute(GET("/api/v2/access-module/all-filtered-paged/{key}/{offset}/{limit}"), moduleHandler::filter)
                .andRoute(GET("/api/v2/access-module/code/{code}"), moduleHandler::getByCode)
                .andRoute(POST("/api/v2/access-module/create"), moduleHandler::create)
                .andRoute(PUT("/api/v2/access-module/update"), moduleHandler::update)
                .andRoute(DELETE("/api/v2/access-module/delete"), moduleHandler::delete);
    }
}