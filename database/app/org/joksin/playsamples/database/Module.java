package org.joksin.playsamples.database;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(DatabaseInitializer.class).asEagerSingleton();
    }

}
