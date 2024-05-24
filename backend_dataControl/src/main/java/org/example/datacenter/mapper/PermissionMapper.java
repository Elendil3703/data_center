package org.example.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.datacenter.model.Admin;
import java.util.List;

@Mapper
public interface PermissionMapper {
    void insertPermission(@Param("name") String name, @Param("password") String password, @Param("readable") String readable, @Param("writable") String writable);
    void deletePermission(@Param("name") String name);
    void dropUser(@Param("name") String name);
    void createUser(@Param("name") String name, @Param("password") String password);
    void grantSelectPermission(@Param("tableName") String tableName, @Param("userName") String userName);
    void grantInsertUpdatePermission(@Param("tableName") String tableName, @Param("userName") String userName);
    List<Integer> getIdsWithPermissionZero();
    List<String> getTableNamesByIds(@Param("ids") List<Integer> ids);
    Integer countUserInAdmin(@Param("name") String name);
    Integer countUserInMySQL(@Param("name") String name);
    List<Admin> getAdmins(@Param("offset") int offset, @Param("limit") int limit);
    String getPasswordByName(@Param("name") String name);
}
