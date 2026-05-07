package org.example.auth.blueprint.system.authority.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.utils.MapUtils;
import org.example.auth.blueprint.system.authority.dto.AuthorityDTO;
import org.example.auth.blueprint.system.authority.service.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<?> selectList(@RequestParam Map<String, Object> map) {
        if (map.get("authorityId") == null) {
            return ResponseEntity.ok(authorityService.selectList(MapUtils.convert(map)));
        } else {
            return ResponseEntity.ok(authorityService.selectOne(MapUtils.convert(map)));
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid AuthorityDTO.Create create) {
        return ResponseEntity.ok(authorityService.insert(MapUtils.convert(create)));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid AuthorityDTO.Update update) {
        return ResponseEntity.ok(authorityService.update(MapUtils.convert(update)));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Map<String, String> map) {
        return ResponseEntity.ok(authorityService.delete(MapUtils.convert(map)));
    }
}
