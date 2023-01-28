package com.example.myapplication.dataclass;

public class Year {

    public int id;

    public String title;
    public String description;

    public double grade;

    public int[] partYearId;

    public Year(String title, String desc, double grade, int[] partYearId) {
        this.title = title;
        this.description = desc;
        this.grade = grade;
        this.partYearId = partYearId;
    }
}
