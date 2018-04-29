package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/6.
 */

public class uuid implements Serializable {
    private  String uuid;

    public uuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "uuid{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
