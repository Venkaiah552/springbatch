package com.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Builder
@NamedNativeQuery(name = "user.findAll",
query = "select * from user")
public class User {

    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
   // @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime birthdate;
}
