package com.ragtagger.brag.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by alpesh.rathod on 2/14/2018.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFullUrl {
}
