package com.mahdiyar.service;

import com.mahdiyar.model.dto.user.UserModel;
import com.mahdiyar.model.dto.user.request.CreateUserRequestDto;
import com.mahdiyar.model.dto.user.request.UpdateUserRequestDto;
import com.mahdiyar.model.general.PagedQuery;
import com.mahdiyar.model.general.PagedQueryResponse;
import org.springframework.stereotype.Service;

/**
 * @author mahdiyar
 */
@Service
public class UserService {
    public UserModel create(CreateUserRequestDto requestDto) {
        return null;
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
