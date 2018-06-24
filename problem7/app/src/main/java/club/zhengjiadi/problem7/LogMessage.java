package club.zhengjiadi.problem7;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * Created by zjdzn on 2018/6/24.
 */

public class LogMessage extends DataSupport {

    int id;
    Date createTime;
    String log;

    public LogMessage() {
    }

    public LogMessage(int id, Date createTime, String log) {
        this.id = id;
        this.createTime = createTime;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", log='" + log + '\'' +
                '}';
    }
}
