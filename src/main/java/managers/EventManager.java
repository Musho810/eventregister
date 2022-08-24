package managers;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class EventManager {
    private final Connection connection;

    public EventManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void add(Event event) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into event (name,place,price,is_online,event_type) Values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getPlace());
        preparedStatement.setDouble(3, event.getPrice());
        preparedStatement.setBoolean(4, event.isOnline());
        preparedStatement.setString(5, event.getEventType().name());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            event.setId(id);
        }
    }

    public void showAllEvents() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From event");
        String defaultType;

        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName((resultSet.getString("name")));
            event.setPlace((resultSet.getString("place")));
            event.setPrice((resultSet.getDouble("price")));
            event.setOnline((resultSet.getBoolean("is_online")));
            defaultType = resultSet.getString("event_type");
            switch (defaultType.toUpperCase()) {
                case "CONCERT" -> event.setEventType(EventType.CONCERT);
                case "EXHIBITION" -> event.setEventType(EventType.EXHIBITION);
                case "KINO" -> event.setEventType(EventType.KINO);
                default -> event.setEventType(EventType.EVENT_NOT_FOUND);
            }
            events.add(event);
        }
        for (Event event : events) {
            System.out.println(event);
        }
    }


    public void findEventByName(String nameEvent) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From event");


        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            Event event = new Event();

            event.setName((resultSet.getString("name")));
            events.add(event);
        }
        for (Event event : events) {
            if (event.getName().equals(nameEvent)) {
                System.out.println(event);
            }

        }
    }

    public void writeEventsToExcel(String fileDir) throws IOException, InvalidFormatException, SQLException {
        File directory = new File(fileDir);
        if (directory.isFile()) {
            throw new RuntimeException("file Dir must be a Directory!");
        }
        File excelFile = new File(directory, "events  " + System.currentTimeMillis() + ".xlsx");
        excelFile.createNewFile();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("events");
        Row headerRow = sheet.createRow(0);

        Cell nameCell = headerRow.createCell(0);
        nameCell.setCellValue("eventname");

        Cell placeCell = headerRow.createCell(1);
        placeCell.setCellValue("eventplace");
        Cell priceCell = headerRow.createCell(2);
        priceCell.setCellValue("price");
        Cell isOnlineCell = headerRow.createCell(3);
        isOnlineCell.setCellValue("isOnline");


        Cell typeCell = headerRow.createCell(4);
        typeCell.setCellValue("eventType");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From event");
        String defaultType = null;

        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName((resultSet.getString("name")));
            event.setPlace((resultSet.getString("place")));
            event.setPrice((resultSet.getDouble("price")));
            event.setOnline((resultSet.getBoolean("is_online")));
            defaultType = resultSet.getString("event_type");
            switch (defaultType.toUpperCase()) {
                case "CONCERT" -> event.setEventType(EventType.CONCERT);
                case "EXHIBITION" -> event.setEventType(EventType.EXHIBITION);
                case "KINO" -> event.setEventType(EventType.KINO);
                default -> event.setEventType(EventType.EVENT_NOT_FOUND);
            }
            events.add(event);
        }
        int k = 0;
        for (Event event : events) {


            Row row = sheet.createRow(k + 1);
            row.createCell(0).setCellValue(event.getName());
            row.createCell(1).setCellValue(event.getPlace());
            row.createCell(2).setCellValue(event.getPrice());
            row.createCell(3).setCellValue(event.isOnline());
            row.createCell(4).setCellValue(event.getEventType().name());

        }
        workbook.write(new FileOutputStream(excelFile));
        System.out.println("Excell was created successfuly");
    }
}
