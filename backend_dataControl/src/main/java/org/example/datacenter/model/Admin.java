package org.example.datacenter.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data

public class Admin {

    private int adminID;
    private String name;
    private String readable;
    private String writable;
    private String password;
}
