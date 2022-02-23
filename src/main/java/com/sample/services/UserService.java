package com.sample.services;

import com.sample.models.User;
import com.sample.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public
class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void add(String firstName, String lastName) {
        User user = new User(null, firstName, lastName);
        userRepository.saveAndFlush(user);
        // ここで非検査例外発生
         throw new RuntimeException();
    }
}
