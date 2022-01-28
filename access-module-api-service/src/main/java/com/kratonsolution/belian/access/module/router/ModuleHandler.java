package com.kratonsolution.belian.access.module.router;

import com.google.common.base.Strings;
import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.api.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModuleHandler {

    @Autowired
    private ModuleService moduleService;

    Mono<ServerResponse> create(ServerRequest request) {

        return moduleService.create(request.bodyToMono(ModuleCreateCommand.class))
                .flatMap(data -> {

                    Map<String, Object> map = new HashMap<>();
                    map.put("success", Boolean.TRUE);
                    map.put("module", data);

                    return ServerResponse.ok().body(Mono.just(map), Map.class);
                })
                .onErrorResume(e->Mono.just(e.getMessage())
                .flatMap(error->{

                    Map<String, Object> map = new HashMap<>();
                    map.put("success", Boolean.FALSE);
                    map.put("message", error);

                    return ServerResponse.ok().body(Mono.just(map), Map.class);
                })
        );
    }

    Mono<ServerResponse> update(ServerRequest request) {

        return moduleService.update(request.bodyToMono(ModuleUpdateCommand.class))
                .flatMap(data -> {

                    Map<String, Object> map = new HashMap<>();
                    map.put("success", Boolean.TRUE);
                    map.put("module", data);

                    return ServerResponse.ok().body(Mono.just(map), Map.class);
                })
                .onErrorResume(ex-> Mono.just(ex.getMessage())
                    .flatMap(error->{

                        Map<String, Object> map = new HashMap<>();
                        map.put("success", Boolean.FALSE);
                        map.put("message", error);
                        return ServerResponse.ok().body(Mono.just(map), Map.class);
                })
        );
    }

    Mono<ServerResponse> delete(ServerRequest request) {

        return moduleService.delete(request.bodyToMono(ModuleDeleteCommand.class))
                .flatMap(data -> {

                    Map<String, Object> map = new HashMap<>();
                    map.put("success", Boolean.TRUE);
                    map.put("module", data);

                    return ServerResponse.ok().body(Mono.just(map), Map.class);
                })
                .onErrorResume(ex-> Mono.just(ex.getMessage())
                        .flatMap(error->{

                            Map<String, Object> map = new HashMap<>();
                            map.put("success", Boolean.FALSE);
                            map.put("message", error);
                            return ServerResponse.ok().body(Mono.just(map), Map.class);
                        })
        );
    }

    Mono<ServerResponse> getByCode(ServerRequest request) {

        return ServerResponse.ok().body(moduleService.getByCode(Mono.just(request.pathVariable("code"))), ModuleData.class);
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(moduleService.getAll().onErrorResume(e-> Flux.empty()), ModuleData.class);
    }

    Mono<ServerResponse> getAllPaged(ServerRequest request) {

        return ServerResponse.ok().body(
                moduleService.getAll(getInt(request.pathVariable("offset")),
                                     getInt(request.pathVariable("limit")))
                                     .onErrorResume(e-> Flux.empty()), ModuleData.class);
    }

    Mono<ServerResponse> filterAll(ServerRequest request) {

        return ServerResponse.ok().body(moduleService.getAll(request.pathVariable("key")), ModuleData.class);
    }

    Mono<ServerResponse> filter(ServerRequest request) {

        Mono<ModuleFilter> param = Mono.fromSupplier(()->{

            if(Strings.isNullOrEmpty(request.pathVariable("key"))) {
                new RuntimeException("filter parameter cannot be empty");
            }

            return ModuleFilter.builder()
                    .key(request.pathVariable("key"))
                    .limit(getInt(request.pathVariable("limit")))
                    .offset(getInt(request.pathVariable("offset")))
                    .build();
        });

        return ServerResponse.ok().body(moduleService.getAll(param), ModuleData.class);
    }

    private int getInt(String param) {
        if(!Strings.isNullOrEmpty(param))
            return Integer.parseInt(param);

        return 0;
    }
}
