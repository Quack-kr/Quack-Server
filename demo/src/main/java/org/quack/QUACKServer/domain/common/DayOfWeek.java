package org.quack.QUACKServer.domain.common;

public enum DayOfWeek {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private String day;

    private DayOfWeek(String day) {
        this.day = day;
    }
}
