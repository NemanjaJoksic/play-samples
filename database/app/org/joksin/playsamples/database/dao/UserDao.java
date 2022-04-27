package org.joksin.playsamples.database.dao;

import com.google.inject.ImplementedBy;
import org.joksin.playsamples.database.model.User;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ImplementedBy(JdbcUserDao.class)
public interface UserDao {

    List<User> getAllUsers();

    CompletionStage<List<User>> getAllUsersAsync();

    CompletionStage<User> createUser(User user);

}
