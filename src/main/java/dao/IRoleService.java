package dao;

import entity.Role;
import exception.ProjectException;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role create(Role role) throws ProjectException;
    Optional<Role> findById(int roleId) throws ProjectException;
    List<Role> findAll() throws ProjectException;
    Role update(Role role) throws ProjectException;
    boolean delete(int roleId) throws ProjectException;
}
