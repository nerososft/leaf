package nero.intel.com.leaf.entity;

import java.io.Serializable;

/**
 * Created by ny on 2018/3/6.
 */

public class Sms implements Serializable {
    private String Phone;
    private String BusinessId;

    public Sms(String phone, String businessId) {
        Phone = phone;
        BusinessId = businessId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "Phone='" + Phone + '\'' +
                ", BusinessId='" + BusinessId + '\'' +
                '}';
    }
}
