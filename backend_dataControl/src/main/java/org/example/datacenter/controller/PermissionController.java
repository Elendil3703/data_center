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

    @PutMapping("/update")
    public void updatePermission(@RequestParam String name,
                                 @RequestParam String password,
                                 @RequestParam String readable,
                                 @RequestParam String writable) {
        permissionService.updatePermission(name, password, readable, writable);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String name) {
        permissionService.deleteUser(name);
    }

    @GetMapping("/show")
    public List<Admin> getAdmins(@RequestParam int page, @RequestParam int size) {
        return permissionService.getAdmins(page, size);
    }
}
