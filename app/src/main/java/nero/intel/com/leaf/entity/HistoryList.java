package nero.intel.com.leaf.entity;

import java.io.Serializable;
import java.util.List;

public class HistoryList implements Serializable{
    private Count count;
    private List<HistroyItem> result;


    public HistoryList() {
    }

    public HistoryList(Count count, List<HistroyItem> result) {
        this.count = count;
        this.result = result;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public List<HistroyItem> getResult() {
        return result;
    }

    public void setResult(List<HistroyItem> result) {
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
