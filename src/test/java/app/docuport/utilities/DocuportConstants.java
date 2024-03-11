package app.docuport.utilities;

public class DocuportConstants {
    public static final String USERNAME_CLIENT = "b1g1_client@gmail.com";
    public static final String USERNAME_ADVISOR = "b1g1_advisor@gmail.com";
    public static final String USERNAME_SUPERVISOR = "b1g1_supervisor@gmail.com";
    public static final String USERNAME_EMPLOYEE = "b1g1_employee@gmail.com";

    private static final String PASSWORD = "Group1";

    public static final String ERROR_MESSAGE_FOR_MISSING_PASSWORD ="Please enter your password";
    public static final String LOGO_DOCUPORT ="Docuport";
    public static final String LOGIN_PAGE_URL ="https://beta.docuport.app/login";
    public static final String HOME_PAGE_URL ="https://beta.docuport.app/";
    public static final String HOME_PAGE_TITLE ="Docuport";
    public static final String USERNAME_PLACEHOLDER ="Username or email";
    public static final String PASSWORD_PLACEHOLDER ="Password";
    public static final String USERNAME_EXPECTED_ERROR_MESSAGE ="Please enter your username or email address";
    public static final String PASSWORD_EXPECTED_ERROR_MESSAGE ="Please enter your password";
    public static final String RESET_PASSWORD_URL = "https://dev.docuport.app/reset-password";
    public static final String RESET_PASSWORD_MESSAGE = "Enter the email address associated with your account";
    public static final String RESET_PASSWORD_EMAIL = "forgotpasswordg1@gmail.com";

    public static String getUsername() {
        return USERNAME_ADVISOR;
    }

    public static String getPassword() { return PASSWORD; }

    public static final int extraSmall = 3;
    public static final int small = 5;
    public static final int medium = 7;
    public static final int large = 10;
    public static final int extraLarge = 20;
}
