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

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0
 */
@Configuration
public class ModuleRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> getAllModules(ModuleHandler handler) {

        return RouterFunctions
                .route(GET("/api/v2/access-module/all"), handler::getAll)
                .andRoute(GET("/api/v2/access-module/all-paged/{offset}/{limit}"), handler::getAllPaged)
                .andRoute(GET("/api/v2/access-module/filter/{key}"), handler::filter)
                .andRoute(GET("/api/v2/access-module/filter-paged/{key}/{offset}/{limit}"), handler::filterPaged)
                .andRoute(GET("/api/v2/access-module/code/{code}"), handler::getByCode)
                .andRoute(POST("/api/v2/access-module/create"), handler::create)
                .andRoute(PUT("/api/v2/access-module/update"), handler::update)
                .andRoute(DELETE("/api/v2/access-module/delete"), handler::delete);
    }
}