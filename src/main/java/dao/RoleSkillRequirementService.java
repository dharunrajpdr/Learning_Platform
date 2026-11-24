package dao;

import entity.RoleSkillRequirement;
import exception.DatabaseOperationException;
import exception.InvalidSkillLevelException;
import exception.RecordNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleSkillRequirementService implements IRoleSkillRequirementService {
    @Override
    public RoleSkillRequirement create(RoleSkillRequirement requirement)
            throws DatabaseOperationException, InvalidSkillLevelException {
        validate(requirement);
        String sql =
                "INSERT INTO role_skill_requirements (role_id, skill_id, required_level) "
                        + "VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, requirement);
            statement.executeUpdate();
            return requirement;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to add role-skill requirement.", exception);
        }
    }

    @Override
    public List<RoleSkillRequirement> findByRoleId(int roleId)
            throws DatabaseOperationException {
        String sql =
                "SELECT role_id, skill_id, required_level "
                        + "FROM role_skill_requirements WHERE role_id = ?";
        List<RoleSkillRequirement> requirements = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    requirements.add(map(resultSet));
                }
            }
            return requirements;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to retrieve role-skill requirements.", exception);
        }
    }

    @Override
    public RoleSkillRequirement update(RoleSkillRequirement requirement)
            throws DatabaseOperationException, InvalidSkillLevelException,
            RecordNotFoundException {
        validate(requirement);
        String sql =
                "UPDATE role_skill_requirements SET required_level = ? "
                        + "WHERE role_id = ? AND skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, requirement.getRequiredLevel());
            statement.setInt(2, requirement.getRoleId());
            statement.setInt(3, requirement.getSkillId());
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException("Role-skill requirement not found.");
            }
            return requirement;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to update role-skill requirement.", exception);
        }
    }

    @Override
    public boolean delete(int roleId, int skillId) throws DatabaseOperationException {
        String sql =
                "DELETE FROM role_skill_requirements "
                        + "WHERE role_id = ? AND skill_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            statement.setInt(2, skillId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to delete role-skill requirement.", exception);
        }
    }

    private void validate(RoleSkillRequirement requirement)
            throws InvalidSkillLevelException {
        if (requirement == null) {
            throw new InvalidSkillLevelException("Role-skill requirement is required.");
        }
        try {
            requirement.validate();
        } catch (IllegalArgumentException exception) {
            throw new InvalidSkillLevelException(
                    "Role-skill requirement is invalid: " + exception.getMessage());
        }
    }

    private void bind(PreparedStatement statement,
                      RoleSkillRequirement requirement) throws SQLException {
        statement.setInt(1, requirement.getRoleId());
        statement.setInt(2, requirement.getSkillId());
        statement.setInt(3, requirement.getRequiredLevel());
    }

    private RoleSkillRequirement map(ResultSet resultSet) throws SQLException {
        return new RoleSkillRequirement(
                resultSet.getInt("role_id"),
                resultSet.getInt("skill_id"),
                resultSet.getInt("required_level"));
    }
}
