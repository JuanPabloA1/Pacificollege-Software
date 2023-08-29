package com.application.main.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/page/{pageNo}/{pageSize}")
    public Page<User> getForPageable(
            @PathVariable int pageNo,
            @PathVariable int pageSize,
            @RequestParam(defaultValue = "tipoEstudiante") String sort) {

        return userService.getUserForPageable(pageNo, pageSize, sort);
    }

    @GetMapping("/late/students/{pageNo}/{pageSize}")
    public Page<User> getForPageableLateUsers(
            @PathVariable int pageNo,
            @PathVariable int pageSize
    ) {
        return userService.getFilterUserByLateEpisode(pageNo, pageSize);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportExcel(@RequestBody User filter) throws Exception {
        byte[] stream = userService.exportSheetExcel(filter);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xls");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(stream);
    }

    @PostMapping("/filtro")
    public Page<User> getFilterOfUser(
            @RequestBody(required = true) User filter,
            @RequestParam int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sort) {
        return userService.getFilterOfUser(filter, pageNo, pageSize, sort);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable("id") int idUser) {
        return userService.deleteUser(idUser);
    }
}
