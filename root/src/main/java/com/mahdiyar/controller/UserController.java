package com.mahdiyar.controller;

import com.mahdiyar.annotation.Api;
import com.mahdiyar.model.dto.user.UserModel;
import com.mahdiyar.model.dto.user.request.CreateUserRequestDto;
import com.mahdiyar.model.dto.user.request.UpdateUserRequestDto;
import com.mahdiyar.model.general.PagedQuery;
import com.mahdiyar.model.general.PagedQueryResponse;
import com.mahdiyar.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author mahdiyar
 */
@Api(value = "Users Controller")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("Create User")
    @PostMapping
    public ResponseEntity<UserModel> create(CreateUserRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(requestDto));
    }

    @ApiOperation("Read Users")
    @GetMapping
    public ResponseEntity<PagedQueryResponse<UserModel>> read(PagedQuery pagedQuery) {
        return ResponseEntity.ok(userService.read(pagedQuery));
    }

    @ApiOperation("Read User")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> read(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @ApiOperation("Update User")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(
            @PathVariable("id") String id,
            @RequestBody UpdateUserRequestDto requestDto) {
        return ResponseEntity.ok(userService.update(id, requestDto));
    }

    @ApiOperation("Delete User")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
