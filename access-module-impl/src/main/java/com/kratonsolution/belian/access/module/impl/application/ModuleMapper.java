package com.kratonsolution.belian.access.module.impl.application;

import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.impl.model.Module;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @sinch 2.0
 */
@Mapper
public interface ModuleMapper {
    
	ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);
	
    ModuleData toData(@NonNull Module module);
    
    List<ModuleData> toDatas(@NonNull List<Module> modules);
}
