package com.tfg.mariomh.v2.myApi.tasks.interfaces;

public interface ITask {

    // @Scheduled(initialDelay = 1000, fixedRate = 1000000)
    // @Scheduled(cron = DO_VAR)
    static final String DO_YEARLY = "@yearly";
    static final String DO_ANNUALLY = "@annually";
    static final String DO_MONTHLY = "@monthly";
    static final String DO_WEEKLY = "@weekly";
    static final String DO_DAILY = "@daily";
    static final String DO_MIDNIGHT = "@midnight";
    static final String DO_HOURLY = "@hourly";
    static final String EVERY_DAY_AT_TEN_AM = "0 0 10 * * *";

    void process();

    default void run(){
        process();
    }

}
