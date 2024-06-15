package org.example.datacenter.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class FilterDataRequest {
    private String tableName;
    private String columnName;
    private String minValue;
    private String maxValue;
}
