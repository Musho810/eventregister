package manager;

import db.DBConnectionProvider;
import model.Event;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    private final EventManager eventManager = new EventManager();

    public void add(User user) {
        String sql = "Insert into user (name,surname,email,event_id) Values (?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getEvent().getId());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        String sql = "SELECT * From user";
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName((resultSet.getString("name")));
        user.setSurname((resultSet.getString("surname")));
        user.setEmail((resultSet.getString("email")));
        int eventId = resultSet.getInt("event_id");
        Event event = eventManager.getById(eventId);
        user.setEvent(event);
        return user;
    }

}

