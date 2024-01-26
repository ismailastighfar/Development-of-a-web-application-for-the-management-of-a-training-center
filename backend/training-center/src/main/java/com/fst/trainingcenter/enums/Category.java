package com.fst.trainingcenter.enums;

public enum Category {
    SOFTWARE_ENGINEERING("Software Engineering"),
    ARTIFICIAL_INTELLIGENCE("Artificial Intelligence"),
    WEB_DEVELOPMENT("Web Development"),
    DATABASE_SYSTEMS("Database Systems"),
    NETWORKING("Networking"),
    MACHINE_LEARNING("Machine Learning"),
    SECURITY("Security"),
    DATA_SCIENCE("Data Science"),
    CLOUD_COMPUTING("Cloud Computing"),
    MOBILE_APP_DEVELOPMENT("Mobile App Development");
    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
