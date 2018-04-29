package nero.intel.com.leaf.entity;

import java.io.Serializable;

public class Count implements Serializable {
     private Integer total;
     private Integer left;


    public Count() {
    }

    public Count(Integer total, Integer left) {
        this.total = total;
        this.left = left;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return "Count{" +
                "total=" + total +
                ", left=" + left +
                '}';
    }
}
