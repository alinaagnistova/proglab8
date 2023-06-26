package org.example.dtp;

import org.example.data.SpaceMarine;
import java.io.Serializable;
import java.util.Objects;
import java.util.Locale;

public class Request implements Serializable {
    private static final long serialVersionUID = 7L;

    private String commandName;
    private String args = "";
    private SpaceMarine object = null;
    private User user;
//    private Locale locale;


    public Request(String commandName, String args, User user) {
        this.commandName = commandName.trim();
        this.args = args;
        this.user = user;
//        this.locale = locale;
    }

    public Request(String commandName, User user, SpaceMarine object) {
        this.commandName = commandName.trim();
        this.object = object;
        this.user = user;
//        this.locale = locale;
    }

    public Request(String commandName, String args, User user, SpaceMarine object) {
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.object = object;
        this.user = user;
//        this.locale = locale;
    }
    public boolean isEmpty() {
        return commandName.isEmpty() && args.isEmpty() && object == null;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public SpaceMarine getObject() {
        return object;
    }

    public User getUser() {
        return user;
    }
//    public Locale getLocale() {
//        return locale;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(commandName, request.commandName) && Objects.equals(args, request.args) && Objects.equals(object, request.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandName, args, object);
    }

    @Override
    public String toString(){
        return "Request[" + commandName +
                (args.isEmpty()
                        ? ""
                        : "," + args ) +
                ((object == null)
                        ? "]"
                        : "," + object + "]");
    }
}