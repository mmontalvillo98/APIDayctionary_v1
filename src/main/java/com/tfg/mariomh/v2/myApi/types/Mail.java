package com.tfg.mariomh.v2.myApi.types;

public enum Mail {

    VERIFICATION("Verify account", "Verify MYPAGE account", "structures/verification-mail.html"),
    PASSWORD("New MY_PAGE password", "Your password has been changed", "structures/new-password-mail.html"),
    AVAILABLE("New game available", "New MYPAGE game available", "structures/available-mail.html");

    private String action;
    private String subject;
    private String template;

    Mail(String action, String subject, String template){
        this.action = action;
        this.subject = subject;
        this.template = template;
    }

    public String getAction() {
        return action;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplate() {
        return template;
    }
}
