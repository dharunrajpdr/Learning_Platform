package entity;

import java.util.Objects;

public class Course {
    private int courseId;
    private String courseName;
    private String description;
    private int capacity;
    private String status;

    public Course() {
    }

    public Course(int courseId, String courseName, String description, int capacity, String status) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.capacity = capacity;
        this.status = status;
        validate();
    }

    public void validate() {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name is required.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Course capacity must be greater than zero.");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Course status is required.");
        }
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && capacity == course.capacity && Objects.equals(courseName, course.courseName) && Objects.equals(description, course.description) && Objects.equals(status, course.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, description, capacity, status);
    }
}
