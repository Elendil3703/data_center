package org.example.datacenter.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class FilterDataWrapper {
    private FilterDataRequest params;
}
