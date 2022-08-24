package managers;

import db.DBConnectionProvider;
import model.User;
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
import java.util.Scanner;

public class UserManager {
    private Connection connection;
    Scanner scanner = new Scanner(System.in);

    public UserManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }


    public void add(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user (name,surname,email,event_id) Values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setInt(4, user.getEventid());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
        System.out.println("User created");
    }

    public void showUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setEventid(resultSet.getInt("event_id"));
            users.add(user);
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void findByEmail(String mailUser) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setEmail(resultSet.getString("email"));

            users.add(user);
        }
        for (User user : users) {
            if (user.getEmail().equals(mailUser)) {
                System.out.println(user);
            }

        }

    }

    public void writeUsersToExcel(String fileDir) throws IOException, InvalidFormatException, SQLException {
        File directory = new File(fileDir);
        if (directory.isFile()) {
            throw new RuntimeException("file Dir must be a Directory!");
        }
        File excelFile = new File(directory, "users  " + System.currentTimeMillis() + ".xlsx");
        excelFile.createNewFile();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("users");
        Row headerRow = sheet.createRow(0);

        Cell nameCell = headerRow.createCell(0);
        nameCell.setCellValue("name");

        Cell surnameCell = headerRow.createCell(1);
        surnameCell.setCellValue("surname");

        Cell emailCell = headerRow.createCell(2);
        emailCell.setCellValue("email");

        Cell eventidCell = headerRow.createCell(3);
        eventidCell.setCellValue("event_id");


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * From user");


        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setEventid(resultSet.getInt("event_id"));

            users.add(user);
        }
        int k = 1;
        for (User event : users) {
k++;

            Row row = sheet.createRow(k + 1);
            row.createCell(0).setCellValue(event.getName());
            row.createCell(1).setCellValue(event.getSurname());
            row.createCell(2).setCellValue(event.getEmail());
            row.createCell(3).setCellValue(event.getEventid());

        }
        workbook.write(new FileOutputStream(excelFile));
        System.out.println("Excell was created successfuly");
    }
}

