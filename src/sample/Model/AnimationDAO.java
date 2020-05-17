package sample.Model;

import sample.Utils.Constant;

import java.sql.*;

public class AnimationDAO {
    final static String TABLE = "animation";

    public String add(Animation animation){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, Constant.DB_USERNAME, "");

            preparedStatement = connection.prepareStatement("INSERT INTO " + TABLE +
                    " (title, author, rating, platform, genre, user_id) VALUES (?,?,?,?,?,?)");

            preparedStatement.setString(1, animation.getTitle());
            preparedStatement.setString(2, animation.getAuthor());
            preparedStatement.setDouble(3, animation.getRating());
            preparedStatement.setString(4, animation.getPlatform());
            preparedStatement.setString(5, animation.getGenre());
            preparedStatement.setInt(6, animation.getUserId());
            preparedStatement.executeUpdate();

            return "Successfully created new entry";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new entry";
        }
    }

    public ResultSet searchByTitle(String title, User user){
        String query = "";
        if (user.isAdmin()) {
            if (title.equals("")) {
                query = "SELECT * FROM " + TABLE;
            } else {
                query = "SELECT * FROM " + TABLE + " WHERE title LIKE '" + title + "'";
            }
        } else {
            if (title.equals("")) {
                query = "SELECT * FROM " + TABLE + " WHERE user_id = '" + user.getId() + "'";
            } else {
                query = "SELECT * FROM " + TABLE + " WHERE user_id = '" + user.getId() + "' AND title LIKE '" + title + "'";
            }
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, Constant.DB_USERNAME, "");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void editById(Animation animation){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, Constant.DB_USERNAME, "");
            preparedStatement = connection.prepareStatement("UPDATE " + TABLE +
                    " SET title=?, author=?, rating=?, platform=?, genre=?, user_id=?");
            preparedStatement.setString(1, animation.getTitle());
            preparedStatement.setString(2, animation.getAuthor());
            preparedStatement.setDouble(3, animation.getRating());
            preparedStatement.setString(4, animation.getPlatform());
            preparedStatement.setString(5, animation.getGenre());
            preparedStatement.setInt(6, animation.getUserId());
            preparedStatement.executeUpdate();

            System.out.println("Successfully updated animation");
        } catch (SQLException e) {
            System.out.println("Failure updating animation");
            e.printStackTrace();
        }
    }
    public void deleteById(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, Constant.DB_USERNAME, "");
            preparedStatement = connection.prepareStatement("DELETE FROM " + TABLE + " WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Successfully deleted movie");
        } catch (SQLException e) {
            System.out.println("Failure deleting movie");
            e.printStackTrace();
        }
    }
}
