package com.dashwood.myspring.model.student.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Student {

    private String id;
    private String name;
    private Integer age;

}
