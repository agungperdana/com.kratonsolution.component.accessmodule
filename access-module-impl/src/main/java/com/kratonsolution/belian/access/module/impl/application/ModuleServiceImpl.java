package com.kratonsolution.belian.access.module.impl.application;

import com.google.common.base.Strings;
import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.api.application.*;
import com.kratonsolution.belian.access.module.impl.model.Module;
import com.kratonsolution.belian.access.module.impl.repository.ModuleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 * @since 2.0
 */
@Slf4j
@Service
public class ModuleServiceImpl implements ModuleService {
    
    @Autowired
    private ModuleRepository repo;

    @Transactional(propagation = Propagation.REQUIRED)
    public Mono<ModuleData> create(Mono<ModuleCreateCommand> monoCommand) {

        return monoCommand.flatMap(command -> {

            return repo.findOneByCode(command.getCode())
                    .switchIfEmpty(Mono.just(new Module(command.getCode(), command.getName(), command.getModuleGroup(), command.getNote(), command.isEnabled())))
                    .flatMap(module -> {

                        if(Strings.isNullOrEmpty(module.getId())) {

                            module.setId(UUID.randomUUID().toString());
                            repo.save(module).subscribe();
                            log.info("Creating new Module {}", module);

                            return Mono.just(ModuleMapper.INSTANCE.toData(module));
                        }
                        else {
                            return Mono.error(new RuntimeException("Module already exist!"));
                        }
                    });
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Mono<ModuleData> update(Mono<ModuleUpdateCommand> monoCommand) {

        return monoCommand.flatMap(command->{

            return repo.findOneByCode(command.getCode())
                    .switchIfEmpty(Mono.empty())
                    .flatMap(module -> {

                        module.setName(command.getName());
                        module.setModuleGroup(command.getGroup());
                        module.setEnabled(command.isEnabled());
                        module.setNote(command.getNote());

                        repo.save(module).subscribe();

                        log.info("Updating module {}", module);

                        return Mono.just(ModuleMapper.INSTANCE.toData(module));
                    });
            });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Mono<ModuleData> delete(Mono<ModuleDeleteCommand> command) {

        return command.flatMap(c->{

            return repo.findOneByCode(c.getCode()).flatMap(m->{

                repo.delete(m).subscribe();
                return Mono.just(ModuleMapper.INSTANCE.toData(m));
            });
        });
    }
    
    public Mono<ModuleData> getByCode(Mono<String> code) {

        return code.flatMap(c->repo.getOne(c));
    }
    
    public Flux<ModuleData> getAll() {

        return repo.loadAllModule();
    }

    public Flux<ModuleData> getAll(int offset, int limit) {

        return repo.loadAllModule(limit, offset);
    }

	@Override
	public Flux<ModuleData> getAll(@NonNull String key) {

        return repo.loadAllModule(key);
	}

    @Override
    public Flux<ModuleData> getAll(@NonNull Mono<ModuleFilter> filter) {
        return filter.flatMapMany(f -> repo.loadAllModule(f.getKey(), f.getOffset(), f.getLimit()));
    }

    public Mono<Long> count() {

        return repo.count();
    }

    public Mono<Long> count(@NonNull Mono<String> key) {

        return key.flatMap(k -> repo.count(k));
    }
}