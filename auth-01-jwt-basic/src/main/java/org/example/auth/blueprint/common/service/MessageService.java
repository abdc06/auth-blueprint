package org.example.auth.blueprint.common.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 주입을 자동으로 생성해줍니다.
public class MessageService {

    // final로 선언하여 RequiredArgsConstructor가 주입하도록 유도
    private final ReloadableResourceBundleMessageSource source;

    private static ReloadableResourceBundleMessageSource messageSource;

    @PostConstruct
    public void initialize() {
        messageSource = source;
    }

    public static String getMessage(String messageCd) {
        return messageSource.getMessage(messageCd, null, null);
    }

    public static String getMessage(String messageCd, Object... messageArgs) {
        // String... 보다는 Object... 가 유연합니다 (숫자 등 포함)
        return messageSource.getMessage(messageCd, messageArgs, null);
    }
}