package com.paradigma.filmio.api.validator;

import com.paradigma.filmio.api.exception.BadRequestException;
import com.paradigma.filmio.api.model.PageModel;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserPageableValidator implements PageableValidator{

    private final static Set<String> validSortBy = Set.of("username", "createdAt");

    @Override
    public void validate(PageModel pageable) {
        final var sortBy = pageable.getSort();
        if (sortBy == null) {
            pageable.setSort("username");
        }
        if (!validSortBy.contains(sortBy)) {
            throw new BadRequestException("Invalid sort parameter");
        }
    }
}
