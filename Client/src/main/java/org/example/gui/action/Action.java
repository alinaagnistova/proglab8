package org.example.gui.action;


import org.example.dtp.User;
import org.example.gui.GuiManager;
import org.example.utils.Client;

import javax.swing.*;
import java.util.ResourceBundle;

public abstract class Action extends AbstractAction {
    protected ResourceBundle resourceBundle;
    protected User user;
    protected Client client;
    protected GuiManager guiManager;

    public Action(User user, Client client, GuiManager guiManager) {
        this.user = user;
        this.client = client;
        this.guiManager = guiManager;
        this.resourceBundle = ResourceBundle.getBundle("GuiLabels", guiManager.getLocale());
    }
}
