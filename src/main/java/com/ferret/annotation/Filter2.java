package com.ferret.annotation;


@Table("department")
public class Filter2 {
    @Column("id")
    private int id;
    @Column("department_name")
    private String departmentName;

    @Column("leader")
    private String leader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

}