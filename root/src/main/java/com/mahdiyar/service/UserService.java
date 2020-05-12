package com.mahdiyar.service;

import com.mahdiyar.model.dto.user.UserModel;
import com.mahdiyar.model.dto.user.request.CreateUserRequestDto;
import com.mahdiyar.model.dto.user.request.UpdateUserRequestDto;
import com.mahdiyar.model.entities.UserEntity;
import com.mahdiyar.model.general.PagedQuery;
import com.mahdiyar.model.general.PagedQueryResponse;
import com.mahdiyar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author mahdiyar
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel create(CreateUserRequestDto requestDto) {
        UserEntity userEntity = new UserEntity();
        userEntity = userRepository.save(userEntity);
        return new UserModel();
    }

    public PagedQueryResponse<UserModel> read(PagedQuery pagedQuery) {
        return null;
    }

    public UserModel read(String id) {
        return null;
    }

    public UserModel update(String id, UpdateUserRequestDto requestDto) {
        return null;
    }

    public void delete(String id) {

    }
}
