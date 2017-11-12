package com.ascalonic.vigr;

import android.view.View;

/**
 * Created by HP on 13-11-2017.
 */

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}