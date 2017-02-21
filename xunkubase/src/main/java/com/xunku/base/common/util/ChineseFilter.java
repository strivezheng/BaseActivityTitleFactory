package com.xunku.base.common.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.HashSet;
import java.util.Set;

/**
 * 中文过滤
 * Created by 谢家勋 on 2016/7/20.
 */
public class ChineseFilter implements InputFilter {

    private static Set<String> filterSet = null;

    private static void addUnicodeRangeToSet(Set<String> set, int start, int end) {
        if (set == null) {
            return;
        }
        if (start > end) {
            return;
        }

        for (int i = start; i <= end; i++) {
            filterSet.add(new String(new int[] {
                    i
            }, 0, 1));
        }
    }

    static {
        filterSet = new HashSet<String>();
        // 4E00 - 9FA5
        addUnicodeRangeToSet(filterSet, 0X4E00, 0X9FA5);

    }

    public ChineseFilter() {
        super();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                               int dend) {
        // check black-list set
        if (filterSet.contains(source.toString())) {
            return "";
        }
        if (source.toString().length() > 1) {
            return "";
        }
        return source;
    }

}