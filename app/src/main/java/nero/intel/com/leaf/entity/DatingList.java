package nero.intel.com.leaf.entity;

import java.io.Serializable;
import java.util.List;

public class DatingList implements Serializable{
    private Count count;
    private List<DatingItem> result;


    public DatingList() {
    }

    public DatingList(Count count, List<DatingItem> result) {
        this.count = count;
        this.result = result;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public List<DatingItem> getResult() {
        return result;
    }

    public void setResult(List<DatingItem> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DatingList{" +
                "count=" + count +
                ", result=" + result +
                '}';
    }
}
