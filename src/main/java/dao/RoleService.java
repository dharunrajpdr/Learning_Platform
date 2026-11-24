package dao;

import entity.Role;
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

public class RoleService implements IRoleService {
    @Override
    public Role create(Role role) throws DatabaseOperationException {
        validate(role);
        String sql = "INSERT INTO roles (role_name, description) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, role.getRoleName());
            statement.setString(2, role.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    role.setRoleId(keys.getInt(1));
                }
            }
            return role;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to create role.", exception);
        }
    }

    @Override
    public Optional<Role> findById(int roleId) throws DatabaseOperationException {
        String sql = "SELECT role_id, role_name, description FROM roles WHERE role_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(map(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve role.", exception);
        }
    }

    @Override
    public List<Role> findAll() throws DatabaseOperationException {
        String sql = "SELECT role_id, role_name, description FROM roles ORDER BY role_name";
        List<Role> roles = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(map(resultSet));
            }
            return roles;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to retrieve roles.", exception);
        }
    }

    @Override
    public Role update(Role role)
            throws DatabaseOperationException, RecordNotFoundException {
        validate(role);
        String sql = "UPDATE roles SET role_name = ?, description = ? WHERE role_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getRoleName());
            statement.setString(2, role.getDescription());
            statement.setInt(3, role.getRoleId());
            if (statement.executeUpdate() == 0) {
                throw new RecordNotFoundException("Role not found: " + role.getRoleId());
            }
            return role;
        } catch (SQLException exception) {
            throw new DatabaseOperationException("Unable to update role.", exception);
        }
    }

    @Override
    public boolean delete(int roleId) throws DatabaseOperationException {
        String sql = "DELETE FROM roles WHERE role_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DatabaseOperationException(
                    "Unable to delete role; it may be referenced by employees or skills.",
                    exception);
        }
    }

    private void validate(Role role) throws DatabaseOperationException {
        if (role == null) {
            throw new DatabaseOperationException("Role is required.");
        }
        role.validate();
    }

    private Role map(ResultSet resultSet) throws SQLException {
        return new Role(
                resultSet.getInt("role_id"),
                resultSet.getString("role_name"),
                resultSet.getString("description"));
    }
}
