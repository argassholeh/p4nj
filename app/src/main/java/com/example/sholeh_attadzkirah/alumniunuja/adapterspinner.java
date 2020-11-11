/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;

public class adapterspinner extends ArrayAdapter<String> {

    public adapterspinner(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ?count-1 : count;
    }
}
