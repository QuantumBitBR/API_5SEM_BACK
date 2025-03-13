package com.quantum.stratify.web.dtos;

import com.quantum.stratify.entities.DimTime;
import com.quantum.stratify.entities.DimVariable;

public record CardDTO(Long id, DimVariable variable, DimTime time) {
    
}
