package dao;

import entity.Skill;
import exception.ProjectException;

import java.util.List;
import java.util.Optional;

public interface ISkillService {
    Skill create(Skill skill) throws ProjectException;
    Optional<Skill> findById(int skillId) throws ProjectException;
    List<Skill> findAll() throws ProjectException;
    Skill update(Skill skill) throws ProjectException;
    boolean delete(int skillId) throws ProjectException;
}
