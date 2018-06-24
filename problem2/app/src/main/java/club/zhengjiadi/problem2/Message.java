package club.zhengjiadi.problem2;

/**
 * Created by zjdzn on 2018/6/23.
 */

public class Message {
    private int returnCode;
    private String returnValue;
    private String returnMsg;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "returnCode=" + returnCode +
                ", returnValue='" + returnValue + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
