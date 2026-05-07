package org.example.auth.blueprint.system.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.utils.MapUtils;
import org.example.auth.blueprint.system.user.dto.UserDTO;
import org.example.auth.blueprint.system.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<?> selectList(@RequestParam Map<String, Object> map) {
        if (map.get("userId") == null) {
            return ResponseEntity.ok(userService.selectList(MapUtils.convert(map)));
        } else {
            return ResponseEntity.ok(userService.selectOne(MapUtils.convert(map)));
        }
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid UserDTO.Create create) {
        return ResponseEntity.ok(userService.insert(MapUtils.convert(create)));
    }

    @ResponseBody
    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid UserDTO.Update update) {
        return ResponseEntity.ok(userService.update(MapUtils.convert(update)));
    }

    @ResponseBody
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Map<String, Object> map) {
        return ResponseEntity.ok(userService.delete(MapUtils.convert(map)));
    }
}
