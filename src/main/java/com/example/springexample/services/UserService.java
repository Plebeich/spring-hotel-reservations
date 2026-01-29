package com.example.springexample.services;



import com.example.springexample.dto.UserRequest;
import com.example.springexample.dto.UserResponse;
import com.example.springexample.exeption.BadRequestException;
import com.example.springexample.exeption.CastomException;
import com.example.springexample.mapper.UserMapper;
import com.example.springexample.model.User;
import com.example.springexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StatisticService statisticService;

    @Transactional
    public UserResponse createUser(UserRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Имя: " + request.getUsername() + "занято");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email: " + request.getEmail() + "уже зарегистрирован");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        statisticService.saveUserRegistration(savedUser.getId());

        return userMapper.toResponseDTO(savedUser);
    }

    public UserResponse getUsserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CastomException("User", "id", id));
        return userMapper.toResponseDTO(user);
    }

    public UserResponse getUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CastomException("User","username",username));
        return userMapper.toResponseDTO(user);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(int id, UserRequest request){
        System.out.println("Обновление данных пользователя ID: " + id);
        User user = userRepository.findById(id).orElseThrow(() -> new CastomException("User", "username", id));
        if (userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Имя: " + request.getUsername() + "занято");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email: " + request.getEmail() + "уже зарегистрирован");
        }

        userMapper.updateEntity(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }
    @Transactional
    public void deleteUser(Integer id) {
        System.out.println("Удаление пользователя по ID" + id);
        if (!userRepository.existsById(id)) {
            throw new CastomException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    public boolean userExists(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

}
