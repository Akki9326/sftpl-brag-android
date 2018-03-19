package com.ragtagger.brag.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/*
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by meet.parabiya on 7/22/2015.
 */
 /*
 * Class Usage   :   Singleton class for providing Custom fonts (files)
 */
public class FontProvider {

    private static final Hashtable<String, Typeface> cache = new Hashtable<>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t;
                t = Typeface.createFromAsset(c.getAssets(), String.format("font/%s", name));
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }
}