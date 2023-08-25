package com.jtons.personnel.res;

public class ResResult<T> {
    private String message;
    private String code;
    private T data;

    public static <T> ResResult<T> fail(){
        return rspMsg(ResponseEnum.SERVER_INNER_ERR);
    }
    public static <T> ResResult<T> fail(String msg){
        ResResult<T> message=new ResResult<T>();
        message.setCode("500");
        message.setMessage(msg);
        return message;
    }
    public static <T> ResResult<T> fail(T data){
        ResResult<T> message=new ResResult<T>();
        message.setCode("500");
        message.setMessage("操作失败！");
        message.setData(data);
        return message;
    }
    public static <T> ResResult<T> loginFail(){
        ResResult<T> message =new ResResult<T>();
        message.setCode(ResponseEnum.SERVER_LOGIN_ERR.getCode());
        message.setMessage(ResponseEnum.SERVER_LOGIN_ERR.getMsg());
        return message ;
    }
    public static <T> ResResult<T> success(){
        return rspMsg(ResponseEnum.SUCCESS);
    }
    public static <T> ResResult<T> success(T data){
        ResResult<T> message=new ResResult<T>();
        message.setCode("200");
        message.setMessage("操作成功！");
        message.setData(data);
        return message;
    }
    public static <T> ResResult<T> rspMsg(ResponseEnum responseEnum){
        ResResult<T> message=new ResResult<T>();
        message.setCode(responseEnum.getCode());
        message.setMessage(responseEnum.getMsg());
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
