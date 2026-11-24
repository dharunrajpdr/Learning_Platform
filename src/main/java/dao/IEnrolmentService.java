package dao;

import entity.Enrolment;
import exception.ProjectException;

import java.util.List;

public interface IEnrolmentService {
    Enrolment enrol(int employeeId, int courseId) throws ProjectException;
    Enrolment updateStatus(int enrolmentId, String status) throws ProjectException;
    List<Enrolment> findByEmployeeId(int employeeId) throws ProjectException;
    List<Enrolment> findByCourseId(int courseId) throws ProjectException;
    List<Enrolment> findWaitlistByCourseId(int courseId) throws ProjectException;
}
