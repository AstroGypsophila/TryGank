package com.gypsophila.commonlib.net;

/**
 * Created by Gypsophila on 2016/7/31.
 */
public class Response {

    private boolean error;
    /**
     * 1.远程api执行过程发现异常，定义为大于0的整数
     * 2.app访问api时发生错误，定义为小于0的整数
     */
    private int errorType;
    private String errorMessage;
    private String result;

    public boolean hasError() {
        return error;
    }

    public void setError(boolean hasError) {
        this.error = hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
