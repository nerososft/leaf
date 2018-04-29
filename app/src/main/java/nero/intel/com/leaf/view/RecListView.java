package nero.intel.com.leaf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ny on 2018/3/7.
 */

public class RecListView extends ListView {

    public RecListView(Context context) {
        super(context);
    }

    public RecListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
