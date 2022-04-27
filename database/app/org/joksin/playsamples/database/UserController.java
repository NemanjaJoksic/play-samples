package org.joksin.playsamples.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.joksin.playsamples.database.dao.UserDao;
import org.joksin.playsamples.database.model.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.util.concurrent.CompletionStage;

@Slf4j
public class UserController extends Controller {

    private final UserDao userDao;
    private final ObjectMapper mapper;

    @Inject
    public UserController(UserDao userDao) {
        this.userDao = userDao;
        this.mapper = new ObjectMapper();
    }

    public Result getAllUsers() {
        log.info("Executing UserController.getAllUsers()");
        return Results.ok(Json.toJson(ImmutableMap.of("users", userDao.getAllUsers())));
    }

    public CompletionStage<Result> getAllUsersAsync() {
        log.info("Executing UserController.getAllUsersAsync()");
        return userDao
                    .getAllUsersAsync()
                    .thenApplyAsync(users -> Results.ok(Json.toJson(ImmutableMap.of("users", users))));
    }

    public CompletionStage<Result> createUserAsync() throws JsonProcessingException {
        log.info("Executing UserController.createUserAsync()");
        User user = mapper.treeToValue(request().body().asJson(), User.class);
        return userDao
                .createUser(user)
                .thenApplyAsync(users -> Results.ok(Json.toJson(ImmutableMap.of("user", user))));
    }

}
