package com.kratonsolution.belian.access.module.api.application;

import java.io.Serializable;

import lombok.*;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 * @since 2.0.0
 */
@Getter
@Setter
@Builder
public class ModuleFilter implements Serializable {
 
    private static final long serialVersionUID = 7878294784897732945L;

    private String key;
    
    private int offset;
    
    private int limit;
    
    public String toLikeQuery() {
    	
    	return "%"+getKey()+"%";
    }
}
