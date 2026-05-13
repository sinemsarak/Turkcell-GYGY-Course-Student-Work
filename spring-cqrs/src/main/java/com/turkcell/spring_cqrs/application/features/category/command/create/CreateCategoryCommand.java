package com.turkcell.spring_cqrs.application.features.category.command.create;

import org.hibernate.validator.constraints.Length;

import com.turkcell.spring_cqrs.core.mediator.cqrs.Command;
import com.turkcell.spring_cqrs.core.security.authorization.SecuredOperation;

import jakarta.validation.constraints.NotBlank;

@SecuredOperation(roles = "ADMIN")
public record CreateCategoryCommand(
    @NotBlank @Length(min=3,max=100) String name

) implements Command<CreatedCategoryResponse> {}
