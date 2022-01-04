package com.kratonsolution.belian.access.module.impl.application;

import com.google.common.base.Strings;
import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.api.application.*;
import com.kratonsolution.belian.access.module.impl.model.Module;
import com.kratonsolution.belian.access.module.impl.repository.ModuleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

            Module module = new Module(command.getCode(), command.getName(), command.getGroup(),
                    command.getNote(), command.isEnabled());

            repo.save(module).subscribe();

            log.info("Creating new Module {}", module);

            return Mono.just(ModuleMapper.INSTANCE.toData(module));
        });
    }

    public Mono<ModuleData> update(Mono<ModuleUpdateCommand> monoCommand) {

        return monoCommand.flatMap(command->{

            return repo.findOneByCode(command.getCode()).flatMap(module -> {

                module.setName(command.getName());
                module.setGroup(command.getGroup());
                module.setEnabled(command.isEnabled());
                module.setNote(command.getNote());

                repo.save(module).subscribe();

                log.info("Updating module {}", module);

                return Mono.just(ModuleMapper.INSTANCE.toData(module));
            });
        });
    }

    public Mono<ModuleData> delete(Mono<ModuleDeleteCommand> command) {

        return command.flatMap(c->{

            return repo.findOneByCode(c.getCode()).flatMap(m->{

                repo.delete(m);
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

    public Mono<Long> count() {

        return repo.count();
    }

    public Mono<Long> count(@NonNull Mono<ModuleFilter> filter) {

        return filter.flatMap(f -> {

            if(Strings.isNullOrEmpty(f.getKey())) {
                return count();
            }
            else {
                return repo.count(f.getKey());
            }
        });
    }

	@Override
	public Flux<ModuleData> filter(@NonNull Mono<ModuleFilter> filter) {

        return filter.flatMapMany(f->{

            if(!Strings.isNullOrEmpty(f.getKey()) && f.getPage() != null && f.getSize() != null) {
                return allWithFilterAndPaging(f.getKey(), f.getPage(), f.getSize());
            }
            else if(Strings.isNullOrEmpty(f.getKey()) && f.getPage() != null && f.getSize() != null) {
                return allWithPaging(f.getPage(), f.getSize());
            }
            else {
                return getAll();
            }
        });
	}

    private Flux<ModuleData> allWithPaging(int page, int size) {

        return repo.loadAllModule(PageRequest.of(page, size));
    }

    private Flux<ModuleData> allWithFilterAndPaging(@NonNull String key, int page, int size) {

        return repo.loadAllModule(key, PageRequest.of(page, size));
    }
}
