package com.cmss.www.type;

import java.util.List;

/**
 flink֧�ֵ�CompsiteType ��Ϊ���࣬Tuple�͸�������
 */
public class CompositeType {
    private List<Student> students;
    private long id;

    public CompositeType() {
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
