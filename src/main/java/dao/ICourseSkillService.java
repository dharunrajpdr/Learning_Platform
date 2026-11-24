package dao;

import entity.CourseSkill;
import exception.ProjectException;

import java.util.List;

public interface ICourseSkillService {
    CourseSkill add(CourseSkill courseSkill) throws ProjectException;
    List<CourseSkill> findByCourseId(int courseId) throws ProjectException;
    CourseSkill update(CourseSkill courseSkill) throws ProjectException;
    boolean remove(int courseId, int skillId) throws ProjectException;
}
