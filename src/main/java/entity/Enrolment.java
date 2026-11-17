package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enrolment {
    private int enrolmentId;
    private int employeeId;
    private int courseId;
    private String status;
    private Integer waitlistPosition;
    private LocalDateTime enrolledAt;
    private LocalDateTime completedAt;

    public Enrolment() {
    }

    public Enrolment(int enrolmentId, int employeeId, int courseId, String status, Integer waitlistPosition, LocalDateTime enrolledAt, LocalDateTime completedAt) {
        this.enrolmentId = enrolmentId;
        this.employeeId = employeeId;
        this.courseId = courseId;
        this.status = status;
        this.waitlistPosition = waitlistPosition;
        this.enrolledAt = enrolledAt;
        this.completedAt = completedAt;
        validate();
    }

    public void validate() {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee id must be greater than zero.");
        }
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course id must be greater than zero.");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Enrolment status is required.");
        }
        if (!"WAITLISTED".equals(status) && !"ENROLLED".equals(status) && !"COMPLETED".equals(status) && !"DROPPED".equals(status)) {
            throw new IllegalArgumentException("Enrolment status must be WAITLISTED, ENROLLED, COMPLETED, or DROPPED.");
        }
        if (waitlistPosition != null && waitlistPosition <= 0) {
            throw new IllegalArgumentException("Waitlist position must be a positive number when provided.");
        }
    }

    public int getEnrolmentId() {
        return enrolmentId;
    }

    public void setEnrolmentId(int enrolmentId) {
        this.enrolmentId = enrolmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWaitlistPosition() {
        return waitlistPosition;
    }

    public void setWaitlistPosition(Integer waitlistPosition) {
        this.waitlistPosition = waitlistPosition;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrolment enrolment = (Enrolment) o;
        return enrolmentId == enrolment.enrolmentId && employeeId == enrolment.employeeId && courseId == enrolment.courseId && Objects.equals(status, enrolment.status) && Objects.equals(waitlistPosition, enrolment.waitlistPosition) && Objects.equals(enrolledAt, enrolment.enrolledAt) && Objects.equals(completedAt, enrolment.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrolmentId, employeeId, courseId, status, waitlistPosition, enrolledAt, completedAt);
    }
}
