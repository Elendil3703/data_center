package org.example.datacenter.controller;

import org.example.datacenter.model.Admin;
import org.example.datacenter.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/set")
    public void setPermission(@RequestParam String name,
                              @RequestParam String password,
                              @RequestParam String readable,
                              @RequestParam String writable) {
        permissionService.setPermission(name, password, readable, writable);
    }

    @PostMapping("/update")
    public void updatePermission(@RequestParam String name,
                                 @RequestParam String password,
                                 @RequestParam String readable,
                                 @RequestParam String writable,
                                 @RequestParam String adminName,
                                 @RequestParam String adminPassword) {
        permissionService.updatePermission(name, password, readable, writable, adminName, adminPassword);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestParam String name, @RequestParam String adminName, @RequestParam String adminPassword) {
        permissionService.deleteUser(name, adminName, adminPassword);
    }

    @GetMapping("/show")
    public List<Admin> getAdmins() {
        return permissionService.getAdmins();
    }
}
