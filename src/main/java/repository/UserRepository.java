package repository;

import database.DBConn;
import entity.User;
import utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AppRepository<User> {

    private final static String TABLE_USER = "USER";

    @Override
    public String create(User user) {
        String sql = "INSERT INTO " + TABLE_USER +
                " (name, email) VALUES(?, ?)";
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            List<User> list = new ArrayList<>();
            String sql = "SELECT id, name,  email FROM "
                    + TABLE_USER;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new User(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("email")
                        )
                );
            }
            return Optional.of(list);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        if (readById(user.getId()).isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String sql = "UPDATE " + TABLE_USER +
                    " SET name = ?, email = ?" +
                    " WHERE id = ?";
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                pst.setString(1, user.getName());
                pst.setString(2, user.getEmail());
                pst.setLong(3, user.getId());
                pst.executeUpdate();
                return Constants.DATA_UPDATE_MSG;
            } catch (SQLException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        if (!isIdExists(id)) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String sql = "DELETE FROM " + TABLE_USER +
                    " WHERE id = ?";
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                pst.setLong(1, id);
                pst.executeUpdate();
                return Constants.DATA_DELETE_MSG;
            } catch (SQLException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        String sql = "SELECT id, name, email FROM "
                + TABLE_USER + " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")
            );
            return Optional.of(user);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_USER +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
