package lab11.v2;

import java.io.Serializable;

public class Student implements Serializable {
    int id;
    String name;
    double score;
    // neu co obj thi obj do phai la Serializable
    // con cac kieu du lieu con lai k can vd: array, string,integer,..
}
