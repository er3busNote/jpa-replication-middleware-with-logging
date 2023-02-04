package com.opensw.master.global.error.domain;

import lombok.Getter;

@Getter
public enum ErrorCode {

    HANDLE_ACCESS_DENIED("CM_003", "파라미터가 올바르지 않습니다.", 400),
    JSON_MAPPING_INVALID("CM_006", "JSON 포멧이 일치하지 않습니다.", 400),
    INPUT_KEY_INVALID("CM_004", "입력 Key 값이 올바르지 않습니다.", 400),
    INPUT_VALUE_INVALID("CM_005", "입력 Value 값이 올바르지 않습니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
