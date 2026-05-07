package org.example.auth.blueprint.system.authority.controller;

import org.example.auth.blueprint.common.utils.MapUtils;
import org.example.auth.blueprint.system.authority.service.AuthorityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthorityUserController {

    private final AuthorityUserService authorityUserService;

    @GetMapping("/admin/system/authority/authorityUserMap")
    public String authorityUserMap() {
        return "/admin/system/authority/authorityUserMap";
    }

    @GetMapping("/api/authority/{authorityId}/user")
    public ResponseEntity<?> selectUserList(@PathVariable("authorityId") String authorityId) {
        return ResponseEntity.ok(authorityUserService.selectUserList(authorityId));
    }

    @GetMapping("/api/user/{userId}/authority")
    public ResponseEntity<?> selectAuthorityList(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(authorityUserService.selectAuthorityList(userId));
    }

    @PostMapping("/api/authority/{authorityId}/user")
    public ResponseEntity<?> save(@PathVariable("authorityId") String authorityId, @RequestBody List<Map<String, Object>> mapList) {
        mapList.forEach(map -> map.put("authorityId", authorityId));
        return ResponseEntity.ok(authorityUserService.save(MapUtils.convert(mapList)));
    }


}
