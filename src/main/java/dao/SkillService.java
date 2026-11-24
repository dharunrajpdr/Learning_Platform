package dao;

import entity.Skill;
import exception.DatabaseOperationException;
import exception.RecordNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillService implements ISkillService {
    @Override
    public Skill create(Skill skill) throws DatabaseOperationException {
        validate(skill);
        String sql = "INSERT INTO skills (skill_name, category, description) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, skill.getSkillName());
            statement.setString(2, skill.getCategory());
            statement.setString(3, skill.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    skill.setSkillId(keys.getInt(1));
                }
            }
            return skill;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to create skill.", exception);
        }
    }

    @Override
    public Optional<Skill> findById(int skillId) throws DatabaseOperationException {
        String sql = "SELECT skill_id, skill_name, category, description FROM skills WHERE skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, skillId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(map(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve skill.", exception);
        }
    }

    @Override
    public List<Skill> findAll() throws DatabaseOperationException {
        String sql = "SELECT skill_id, skill_name, category, description FROM skills ORDER BY skill_name";
        List<Skill> skills = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                skills.add(map(resultSet));
            }
            return skills;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve skills.", exception);
        }
    }

    @Override
    public Skill update(Skill skill) throws DatabaseOperationException, RecordNotFoundException {
        validate(skill);
        String sql = "UPDATE skills SET skill_name = ?, category = ?, description = ? WHERE skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, skill.getSkillName());
            statement.setString(2, skill.getCategory());
            statement.setString(3, skill.getDescription());
            statement.setInt(4, skill.getSkillId());
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException("Skill not found: " + skill.getSkillId());
            }
            return skill;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to update skill.", exception);
        }
    }

    @Override
    public boolean delete(int skillId) throws DatabaseOperationException {
        String sql = "DELETE FROM skills WHERE skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, skillId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to delete skill; it may be referenced by required records.", exception);
        }
    }

    private void validate(Skill skill) throws DatabaseOperationException {
        if (skill == null) {
            throw new DatabaseOperationException("Skill is required.");
        }
        skill.validate();
    }

    private Skill map(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category"),
                resultSet.getString("description"));
    }
}
