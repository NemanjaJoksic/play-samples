package org.joksin.playsamples.database.dao;

import lombok.extern.slf4j.Slf4j;
import org.joksin.playsamples.database.ec.DatabaseExecutionContext;
import org.joksin.playsamples.database.model.User;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Singleton
public class JdbcUserDao implements UserDao {

    private final Database db;
    private final DatabaseExecutionContext ec;
    private final AtomicInteger currentId;

    @Inject
    public JdbcUserDao(Database db, DatabaseExecutionContext ec) {
        this.db = db;
        this.ec = ec;

        int maxId = db.withConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM USER");
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        });

        currentId = new AtomicInteger(++maxId);
        log.info("currentId = {}", currentId.get());
    }

    @Override
    public List<User> getAllUsers() {
        log.info("GetAllUsers sync");
        return getAllUsersImpl();
    }

    @Override
    public CompletionStage<List<User>> getAllUsersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("GetAllUsers async");
            return getAllUsersImpl();
        }, ec);
    }

    private List<User> getAllUsersImpl() {
        return db.withConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM USER");
                List<User> users = new ArrayList<>();

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID"));
                    user.setUsername(rs.getString("USERNAME"));
                    user.setAddress(rs.getString("ADDRESS"));
                    user.setCity(rs.getString("CITY"));
                    users.add(user);
                }

                log.info("Parsing data fetched from the database. Connection: {}", connection);
                return users;
            }
        });
    }

    @Override
    public CompletionStage<User> createUser(User user) {
        return CompletableFuture.supplyAsync(() -> db.withTransaction(connection -> {
            log.info("CreateUser async. Connection: {}", connection.toString());
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO USER (\"ID\",\"USERNAME\",\"PASSWORD\",\"ADDRESS\",\"CITY\") VALUES (?,?,?,?,?)")) {
                user.setId(currentId.getAndIncrement());
                ps.setInt(1, user.getId());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getAddress());
                ps.setString(5, user.getCity());

                ps.executeUpdate();
                return user;
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
                throw ex;
            }
        }));
    }

}
