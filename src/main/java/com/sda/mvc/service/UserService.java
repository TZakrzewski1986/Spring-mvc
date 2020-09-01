package com.sda.mvc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sda.mvc.model.dao.UserDao;
import com.sda.mvc.model.dto.UserDto;
import com.sda.mvc.model.dto.UserSecDto;
import com.sda.mvc.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public void addNewUser(UserSecDto user) {
        user.setJoinDate(new Date());
        UserDao userDao = modelMapper.map(user, UserDao.class);
        userDao.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userDao);
    }

    public void deleteUser(UserDto userDto) {
        userRepository.deleteById(userDto.getId());
    }

    public String getNameAndSurnameLoggedUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserDao> userByLogin = userRepository.getUserByLogin(login);

        return userByLogin
                .map(userDao -> userDao.getName() + " " + userDao.getSurname())
                .orElse(login);
    }
}
