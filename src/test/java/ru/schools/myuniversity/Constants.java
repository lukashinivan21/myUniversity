package ru.schools.myuniversity;

import ru.schools.myuniversity.model.Student;

public class Constants {

    private static final Long studentsId = 0L;

    private static final String name1 = "Peter";
    private static final String name2 = "Harry";
    private static final String name3 = "Ronald";
    private static final String name4 = "Scott";

    private static final int age1 = 48;
    private static final int age2 = 45;
    private static final int age3 = 44;
    private static final int age4 = 41;

    public static final int amountOfAddedFaculty = 1;
    public static final String facultyName = "MiddleEarth";
    public static final String facultyColor = "Green";

    public static final Student STUDENT1 = new Student(studentsId, name1, age1);
    public static final Student STUDENT2 = new Student(studentsId, name2, age2);
    public static final Student STUDENT3 = new Student(studentsId, name3, age3);
    public static final Student STUDENT4 = new Student(studentsId, name4, age4);

    public static final int index1ForTests = 1;
    public static final int index2ForTests = 2;
    public static final int index3ForTests = 3;
    public static final int index0ForTests = 0;

    public static final int size = 3;

    public static final long lastIdFromDataBase = 10L;
}
