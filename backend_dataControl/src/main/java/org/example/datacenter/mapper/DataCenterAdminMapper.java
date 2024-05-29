package org.example.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.datacenter.model.DataCenterAdmin;

@Mapper
public interface DataCenterAdminMapper {

    DataCenterAdmin findByName(String name);
    int checkUserExistence(String name);

    DataCenterAdmin getById(Integer integer);
}
