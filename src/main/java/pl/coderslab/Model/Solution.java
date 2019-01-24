package pl.coderslab.Model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Solution {
    private int id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private Exercise exercise;
    private User user;


    public Solution() {
    }


    public Solution(String description) {
        this.description = description;
    }


    public Solution(Exercise exercise, User user) {
        this.exercise = exercise;
        this.user = user;
    }


    public Solution(String description, Exercise exercise, User user) {
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Solution{" + "id=" + id + ", created=" + created + ", updated=" + updated + ", description='" + description + '\'' + ", exercise=" + exercise + ", user=" + user + '}';
    }

    public void setUpdated() {
        this.updated = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created,exercise_id,users_id) VALUES (NOW(),?,?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setInt(1, this.exercise.getId());
            preparedStatement.setLong(2, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET updated=NOW(), description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getSolutionFromResultSet(conn, resultSet);
        }
        return null;
    }

    static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    static public Solution[] loadAllSolutions(Connection conn,int cnt) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution order by id desc limit ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(cnt));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }
    static public Solution[] loadAllSolutionsByUserId(Connection conn, int UserId) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution where users_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }


    static private Solution getSolutionFromResultSet(Connection conn, ResultSet resultSet) throws SQLException {
        Solution loadedSolution = new Solution();
        loadedSolution.id = resultSet.getInt("id");
        loadedSolution.created = resultSet.getObject("created", LocalDateTime.class);
        loadedSolution.updated = resultSet.getObject("updated", LocalDateTime.class);
        loadedSolution.description = resultSet.getString("description");
        loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
        loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
        return loadedSolution;
    }

    public int getId() {
        return id;
    }
}
