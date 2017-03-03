package com.cmss.www.type;

/**
 * flink支持pojo type，要求pojo一定有默认构造函数.
 */
public class PojoType {
    private long id;
    private String name;

    public PojoType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
