package com.quantum.stratify.web.dtos;

import java.util.List;

public record TagDTO(Long id, String nome, List<FatoTagUserStoryDTO> progressoUserStories) {

}
