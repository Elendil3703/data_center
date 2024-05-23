package org.example.datacenter.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateTableRequest {
    private String name;
    private boolean permission;
    private List<TableField> fields;

    public boolean getPermission() {
        return permission;
    }
}
