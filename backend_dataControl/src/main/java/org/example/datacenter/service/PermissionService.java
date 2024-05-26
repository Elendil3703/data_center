package org.example.datacenter.service;

import org.example.datacenter.mapper.PermissionMapper;
import org.example.datacenter.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public void setPermission(String name, String password, String readable, String writable) {
        try {
            // 检查用户是否存在于admin表和MySQL user表中
            if (permissionMapper.countUserInAdmin(name) > 0 || permissionMapper.countUserInMySQL(name) > 0) {
                throw new IllegalArgumentException("User already exists in admin table or MySQL user table.");
            }

            // 从readable字符串中解析出ID列表
            List<Integer> readableList = Arrays.stream(readable.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 获取所有permission为0的ID列表
            List<Integer> idsWithPermissionZero = permissionMapper.getIdsWithPermissionZero();

            // 合并两个ID列表并去重
            List<Integer> combinedReadableList = Stream.concat(readableList.stream(), idsWithPermissionZero.stream())
                    .distinct()
                    .collect(Collectors.toList());

            // 从writable字符串中解析出ID列表
            List<Integer> writableList = Arrays.stream(writable.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 更新admin表中的权限
            permissionMapper.insertPermission(name, password, readable, writable);

            // 创建MySQL用户
            permissionMapper.createUser(name, password);

            // 获取combinedReadableList中的表格名称
            List<String> readableTableNames = permissionMapper.getTableNamesByIds(combinedReadableList);

            // 获取writableList中的表格名称
            List<String> writableTableNames = permissionMapper.getTableNamesByIds(writableList);

            // 为用户授予SELECT权限
            for (String tableName : readableTableNames) {
                permissionMapper.grantSelectPermission(tableName, name);
            }

            // 为用户授予INSERT和UPDATE权限
            for (String tableName : writableTableNames) {
                permissionMapper.grantInsertUpdatePermission(tableName, name);
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    public void updatePermission(String name, String password, String readable, String writable, String adminName, String adminPassword) {
        try {
            // 检查用户是否存在于admin表和MySQL user表中
            if (permissionMapper.countUserInAdmin(name) == 0 || permissionMapper.countUserInMySQL(name) == 0) {
                throw new IllegalArgumentException("User does not exist in admin table or MySQL user table.");
            }
            if(!adminPassword.equals(permissionMapper.getPasswordByName(adminName))) {
                throw new IllegalArgumentException("Admin password is incorrect.");
            }
            // 删除admin表中的用户
            permissionMapper.deletePermission(name);

            // 删除MySQL用户
            permissionMapper.dropUser(name);

            // 从readable字符串中解析出ID列表
            List<Integer> readableList = Arrays.stream(readable.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 获取所有permission为0的ID列表
            List<Integer> idsWithPermissionZero = permissionMapper.getIdsWithPermissionZero();

            // 合并两个ID列表并去重
            List<Integer> combinedReadableList = Stream.concat(readableList.stream(), idsWithPermissionZero.stream())
                    .distinct()
                    .collect(Collectors.toList());

            // 从writable字符串中解析出ID列表
            List<Integer> writableList = Arrays.stream(writable.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 重新插入用户权限到admin表
            permissionMapper.insertPermission(name, password, readable, writable);

            // 重新创建MySQL用户
            permissionMapper.createUser(name, password);

            // 获取combinedReadableList中的表格名称
            List<String> readableTableNames = permissionMapper.getTableNamesByIds(combinedReadableList);

            // 获取writableList中的表格名称
            List<String> writableTableNames = permissionMapper.getTableNamesByIds(writableList);

            // 为用户授予SELECT权限
            for (String tableName : readableTableNames) {
                permissionMapper.grantSelectPermission(tableName, name);
            }

            // 为用户授予INSERT和UPDATE权限
            for (String tableName : writableTableNames) {
                permissionMapper.grantInsertUpdatePermission(tableName, name);
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    public void deleteUser(String name, String adminName, String adminPassword) {
        try {
            // 检查用户是否存在于admin表和MySQL user表中
            if (permissionMapper.countUserInAdmin(name) == 0 || permissionMapper.countUserInMySQL(name) == 0) {
                throw new IllegalArgumentException("User does not exist in admin table or MySQL user table.");
            }
            if(!adminPassword.equals(permissionMapper.getPasswordByName(adminName))) {
                throw new IllegalArgumentException("Admin password is incorrect.");
            }

            // 删除admin表中的用户
            permissionMapper.deletePermission(name);

            // 删除MySQL用户
            permissionMapper.dropUser(name);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    public List<Admin> getAdmins() {
        return permissionMapper.getAdmins();
    }
}
