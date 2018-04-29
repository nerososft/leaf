package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/6.
 */

public class Username implements Serializable {
    private String username;
    private String uuid;

    public Username(String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Username{" +
                "username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
