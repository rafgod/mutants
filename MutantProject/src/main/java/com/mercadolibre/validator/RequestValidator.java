package com.mercadolibre.validator;

import java.util.List;

import com.mercadolibre.DTO.BadRequestResponse;
import com.mercadolibre.DTO.MutantRequestDTO;

public interface RequestValidator {

    public List<BadRequestResponse> validateMutantRequest(MutantRequestDTO request);

}
