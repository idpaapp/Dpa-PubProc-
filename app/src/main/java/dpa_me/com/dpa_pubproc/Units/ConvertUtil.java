package dpa_me.com.dpa_pubproc.Units;

import android.content.Context;

public class ConvertUtil {
    public static int spToPx(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
