package com.nttdata.exchangerateservice.exchangerateservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Currency {
    @Id
    private Integer id;

    private String name;
    private String description;
}
