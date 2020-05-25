package com.wangyu.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: WangYu
 * @date: 2020-04-29
 */

@Data
@AllArgsConstructor
public class ApiResponse {

    private int code;//状态码

    private String message;//响应信息

    private Object data;//响应数据


    public static ApiResponse resMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse OK() {
        return new ApiResponse(STATUS.SUCCESS.getCode(), STATUS.SUCCESS.getMessage(), null);
    }

    public static ApiResponse OKData(Object data) {
        return new ApiResponse(STATUS.SUCCESS.getCode(), STATUS.SUCCESS.getMessage(), data);
    }

    public static ApiResponse resStatus(STATUS status) {
        return new ApiResponse(status.getCode(), status.getMessage(), null);
    }

    //标准状态信息
    public enum STATUS {
        SUCCESS(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        NOT_VALID_PARAM(40005, "Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        NOT_LOGIN(50000, "Not Login");

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        STATUS(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
