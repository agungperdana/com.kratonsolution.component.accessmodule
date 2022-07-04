package com.kratonsolution.belian.access.module.router;

import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.api.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0
 */
@Service
public class ModuleHandler {

    @Autowired
    private ModuleService moduleService;

    Mono<ServerResponse> create(ServerRequest request) {

        return  ServerResponse.ok().body(
                    request.bodyToMono(ModuleCreateCommand.class)
                            .flatMap(command -> moduleService.create(command))
                            .flatMap(data -> ResultHelper.success().put("module", data).mono())
                            .onErrorResume(e -> ResultHelper.failed(e.getMessage()).mono()),
                    Map.class);
    }

    Mono<ServerResponse> update(ServerRequest request) {

        return ServerResponse.ok().body(
                    request.bodyToMono(ModuleUpdateCommand.class)
                            .flatMap(command -> moduleService.update(command))
                            .flatMap(data -> ResultHelper.success().put("module", data).mono())
                            .onErrorResume(e -> ResultHelper.failed(e.getMessage()).mono()),
                    Map.class);
    }

    Mono<ServerResponse> delete(ServerRequest request) {

        return ServerResponse.ok().body(
                request.bodyToMono(ModuleDeleteCommand.class)
                        .flatMap(command -> moduleService.delete(command))
                        .flatMap(data -> ResultHelper.success().put("module", data).mono())
                        .onErrorResume(e -> ResultHelper.failed(e.getMessage()).mono()),
                Map.class);
    }

    Mono<ServerResponse> getByCode(ServerRequest request) {

        return ServerResponse.ok().body(
                moduleService.getByCode(request.pathVariable("code"))
                        .flatMap(data -> ResultHelper.success().put("module", data).mono())
                        .onErrorResume(e -> ResultHelper.failed(e.getMessage()).mono()),
                ModuleData.class);
    }

    Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().body(moduleService.getAll(), ModuleData.class);
    }

    Mono<ServerResponse> getAllPaged(ServerRequest request) {

        return ServerResponse.ok().body(
                moduleService.getAll(
                        NumberHelper.toInt(request.pathVariable("offset")),
                        NumberHelper.toInt(request.pathVariable("limit"))
                ),ModuleData.class);
    }

    Mono<ServerResponse> filter(ServerRequest request) {

        return ServerResponse.ok().body(
                moduleService.filter(request.pathVariable("key")),
                ModuleData.class);
    }

    Mono<ServerResponse> filterPaged(ServerRequest request) {

        return ServerResponse.ok().body(
                moduleService.filter(
                        request.pathVariable("key"),
                        NumberHelper.toInt(request.pathVariable("offset")),
                        NumberHelper.toInt(request.pathVariable("limit"))
                ),
                ModuleData.class);
    }
}
