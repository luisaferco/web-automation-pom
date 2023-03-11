package co.com.training.web.utils;

public enum NavigationOptions {

    REGISTRATION("RegistrationLogin"),
    FILTERING("SearchFilter");

    private final String option;

    NavigationOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
