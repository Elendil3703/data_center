package org.example.datacenter.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DataCenterAdmin {
    private String name;
    private String password;
}
