package com.turkcell.spring_cqrs.application.features.user.command.register;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.turkcell.spring_cqrs.application.features.user.mapper.UserMapper;
import com.turkcell.spring_cqrs.application.features.user.rule.UserBusinessRules;
import com.turkcell.spring_cqrs.core.mediator.cqrs.CommandHandler;
import com.turkcell.spring_cqrs.domain.User;
import com.turkcell.spring_cqrs.persistence.repository.UserRepository;

@Component
public class RegisterCommandHandler implements CommandHandler<RegisterCommand, RegisterResponse> {
    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterCommandHandler(UserRepository userRepository, UserBusinessRules userBusinessRules,
            UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userBusinessRules = userBusinessRules;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse handle(RegisterCommand command) {
        userBusinessRules.userWithSameEmailMustNotExist(command.email());

        User user = userMapper.userFromRegisterCommand(command);
        user.setPassword(passwordEncoder.encode(command.password()));

        userRepository.save(user);

        return userMapper.registerResponseFromUser(user);
    }
}
