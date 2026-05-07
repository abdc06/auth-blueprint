package org.example.auth.blueprint.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommonController {

    @GetMapping(value = {"/", "/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
    public String redirect(HttpServletRequest request) {
        String uri = request.getRequestURI();

        //API 요청은 절대 건드리지 않음
        if (uri.startsWith("/api")) {
            return null; //다음 필터/컨트롤러로 넘김
        }

        //그 외의 모든 '화면' 주소 요청만 index.html로 포워딩
        return "forward:/index.html";
    }

}
