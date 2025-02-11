package org.quack.QUACKServer.domain.common;

public enum DayOfWeek {
    EVERY("매일"),
    MON("월"),
    TUE("화"),
    WED("수"),
    THU("목"),
    FRI("금"),
    SAT("토"),
    SUN("일");

    private String day;

    private DayOfWeek(String day) {
        this.day = day;
    }
}
