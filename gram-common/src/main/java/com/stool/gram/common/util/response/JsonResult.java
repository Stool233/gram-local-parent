package com.stool.gram.common.util.response;

public class JsonResult<T> {

    private int code;
    private String msg;
    private T data;


    /**
     * 成功时候的调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(data);
    }

    /**
     * 失败时候的调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> JsonResult<T> error(CodeMsg codeMsg) {
        return new JsonResult<T>(codeMsg);
    }

    private JsonResult(T data) {
        this.data = data;
    }

    private JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonResult(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
