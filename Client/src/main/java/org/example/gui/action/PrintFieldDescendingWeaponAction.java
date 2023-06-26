package org.example.gui.action;


import org.example.dtp.*;
import org.example.gui.GuiManager;
import org.example.utils.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PrintFieldDescendingWeaponAction extends Action{
    public PrintFieldDescendingWeaponAction(User user, Client client, GuiManager guiManager) {
        super(user, client, guiManager);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Response response = client.sendAndAskResponse(new Request("print_field_descending_weapon_type", "", user));
        if(response.getStatus() == ResponseStatus.OK) JOptionPane.showMessageDialog(null, response.getResponse(), resourceBundle.getString("Result"), JOptionPane.PLAIN_MESSAGE);
        else JOptionPane.showMessageDialog(null, resourceBundle.getString("NoResult"), resourceBundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
    }
}
