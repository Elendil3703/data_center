package org.example.datacenter.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data

public class TablePermissions{

    private int id;
    private String name;
    private boolean permission;  //权限为0为共享表格，1为私有表格
}
