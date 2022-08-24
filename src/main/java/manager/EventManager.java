package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;


public class EventManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public void add(Event event) {
        String sql = "Insert into event (name,place,price,is_online,event_type,eventDate) Values (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getName());
            ps.setString(2, event.getPlace());
            ps.setDouble(3, event.getPrice());
            ps.setBoolean(4, event.isOnline());
            ps.setString(5, event.getEventType().name());
            ps.setString(6, sdf.format(event.getEventDate()));
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                event.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAll() {
        String sql = "SELECT * From event";
        List<Event> events = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getById(int id) {
        String sql = "SELECT * From event where id =" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getEventFromResultSet(resultSet);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private  Event getEventFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        Event event = Event.builder()
                .id(resultSet.getInt("id"))
                .name((resultSet.getString("name")))
                .place((resultSet.getString("place")))
                .price((resultSet.getDouble("price")))
                .isOnline((resultSet.getBoolean("is_online")))
                .eventType(EventType.valueOf(resultSet.getString("event_type")))
                .eventDate(resultSet.getString("event_date")== null ? null : sdf.parse(resultSet.getString("event_date")))
                .build();


        return event;
    }

}
