package com.example.myapplication.dataclass;

public class Year {

    public int id;

    public final String title;
    public final String description;

    public final double grade;

    public final int[] partYearId;

    public Year(String title, String desc, double grade, int[] partYearId) {
        this.title = title;
        this.description = desc;
        this.grade = grade;
        this.partYearId = partYearId;
    }
}
