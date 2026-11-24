package dao;

import entity.Course;
import exception.ProjectException;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course create(Course course) throws ProjectException;
    Optional<Course> findById(int courseId) throws ProjectException;
    List<Course> findAll() throws ProjectException;
    Course update(Course course) throws ProjectException;
    boolean delete(int courseId) throws ProjectException;
}
