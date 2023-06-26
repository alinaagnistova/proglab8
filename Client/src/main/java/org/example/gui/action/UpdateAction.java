package org.example.gui.action;

import org.example.dtp.*;
import org.example.gui.GuiManager;
import org.example.data.*;
import org.example.utils.Client;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.time.LocalDate;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class UpdateAction extends Action {
    LocalDate date = LocalDate.now();

    public UpdateAction(User user, Client client, GuiManager guiManager) {
        super(user, client, guiManager);
    }

    private Long getSelectedId() {
        Long[] userOwnedIds = guiManager.getCollection().stream()
                .filter((s) -> s.getUserLogin().equals(user.name()))
                .map(SpaceMarine::getId)
                .toArray(Long[]::new);

        BorderLayout layout = new BorderLayout();
        JPanel panel = new JPanel(layout);
        JLabel question = new JLabel(resourceBundle.getString("SelectId"));
        JLabel idLabel = new JLabel(resourceBundle.getString("SelectId"));
        JComboBox idField = new JComboBox(userOwnedIds);

        layout.addLayoutComponent(question, BorderLayout.NORTH);
        layout.addLayoutComponent(idLabel, BorderLayout.WEST);
        layout.addLayoutComponent(idField, BorderLayout.EAST);

        JOptionPane.showMessageDialog(null,
                idField,
                resourceBundle.getString("Update"),
                JOptionPane.PLAIN_MESSAGE);
        return (Long) idField.getSelectedItem();
    }

    private SpaceMarine getObject(Long id) {
        return guiManager.getCollection().stream()
                .filter((s) -> s.getId().equals(id))
                .toList().get(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Long id = this.getSelectedId();
        updateJOptionWorker(id);
    }

    public void updateJOptionWorker(Long id) {
        if(id == null) JOptionPane.showMessageDialog(null, resourceBundle.getString("NoObjects"), resourceBundle.getString("Error"), JOptionPane.ERROR_MESSAGE);

        if(!guiManager.getCollection().stream()
                .filter((i) -> i.getId().equals(id))
                .toList()
                .get(0)
                .getUserLogin()
                .equals(user.name())){
            JOptionPane.showMessageDialog(null,
                    resourceBundle.getString("ObjectNotYour"),
                    resourceBundle.getString("Error"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        JLabel mainLabel = new JLabel(resourceBundle.getString("SpaceMarineCreation"));
        JLabel nameLabel = new JLabel(resourceBundle.getString("Name"));
        JLabel cordXLabel = new JLabel(resourceBundle.getString("CoordinateX"));
        JLabel cordYLabel = new JLabel(resourceBundle.getString("CoordinateY"));
        JLabel healthLabel = new JLabel(resourceBundle.getString("Health"));
        JLabel astartesCategoryLabel = new JLabel(resourceBundle.getString("AstartesCategory"));
        JLabel weaponLabel = new JLabel(resourceBundle.getString("Weapon"));
        JLabel meleeWeaponLabel = new JLabel(resourceBundle.getString("MeleeWeapon"));
        JLabel chapterLabel = new JLabel(resourceBundle.getString("ChapterCreation"));
        JLabel chapterNameLabel = new JLabel(resourceBundle.getString("ChapterName"));
        JLabel chapterMarinesCountLabel = new JLabel(resourceBundle.getString("ChapterMarinesCount"));
        JFormattedTextField nameField;
        JFormattedTextField cordXField;
        JFormattedTextField cordYField;
        JFormattedTextField healthField;
        JComboBox astartesCategoryField;
        JComboBox weaponField;
        JComboBox meleeWeaponField;
        JFormattedTextField chapterNameField;
        JFormattedTextField chapterMarinesCountField;
        // Action Listeners
        {
            nameField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    if (text.trim().isEmpty()) {
                        throw new ParseException(resourceBundle.getString("FieldNotEmpty"), 0);
                    }
                    return super.stringToValue(text);
                }
            });
            cordXField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    Integer num;
                    try {
                        num = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        throw new ParseException(resourceBundle.getString("NumberType") + "integer", 0);
                    }
                    if (num <= -595) throw new ParseException(resourceBundle.getString("NumberMustBe") + " " + resourceBundle.getString("More") + " -595", 0);
                    return num;
                }
            });
            cordYField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    Float num;
                    try {
                        num = Float.parseFloat(text);
                    } catch (NumberFormatException e) {
                        throw new ParseException(resourceBundle.getString("NumberType") + " " + "float", 0);
                    }
                    return num;
                }
            });
            healthField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    Float num;
                    try {
                        num = Float.parseFloat(text);
                    } catch (NumberFormatException e) {
                        throw new ParseException(resourceBundle.getString("NumberType") + " " + "float", 0);
                    }
                    if (num <= 0) throw new ParseException(resourceBundle.getString("NumberMustBe") + resourceBundle.getString("More") + " 0", 0);
                    return num;
                }
            });
            astartesCategoryField = new JComboBox(AstartesCategory.values());
            weaponField = new JComboBox(Weapon.values());
            meleeWeaponField = new JComboBox(MeleeWeapon.values());
            chapterNameField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    if (text.trim().isEmpty()) {
                        throw new ParseException(resourceBundle.getString("FieldNotEmpty"), 0);
                    }
                    return super.stringToValue(text);
                }
            });
            chapterMarinesCountField = new JFormattedTextField(new DefaultFormatter() {
                @Override
                public Object stringToValue(String text) throws ParseException {
                    Integer num;
                    try {
                        num = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        throw new ParseException(resourceBundle.getString("NumberType") + " " + "int", 0);
                    }
                    if (num <= 0) throw new ParseException(resourceBundle.getString("NumberMustBe") + resourceBundle.getString("More") + " 0", 0);
                    return num;
                }
            });
        }
        // Default Values
        {
            nameField.setValue("Muhamed");
            cordXField.setValue("2");
            cordYField.setValue("3.0");
            healthField.setValue("50");
            chapterNameField.setValue("Cosmo");
            chapterMarinesCountField.setValue("25");
        }
        // Group Layout
        {
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(mainLabel))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(nameLabel)
                            .addComponent(nameField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(cordXLabel)
                            .addComponent(cordXField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(cordYLabel)
                            .addComponent(cordYField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(healthLabel)
                            .addComponent(healthField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(astartesCategoryLabel)
                            .addComponent(astartesCategoryField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(weaponLabel)
                            .addComponent(weaponField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(meleeWeaponLabel)
                            .addComponent(meleeWeaponField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(chapterLabel))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(chapterNameLabel)
                            .addComponent(chapterNameField))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(chapterMarinesCountLabel)
                            .addComponent(chapterMarinesCountField))
            );
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(mainLabel)
                            .addComponent(nameLabel)
                            .addComponent(cordXLabel)
                            .addComponent(cordYLabel)
                            .addComponent(healthLabel)
                            .addComponent(astartesCategoryLabel)
                            .addComponent(weaponLabel)
                            .addComponent(meleeWeaponLabel)
                            .addComponent(chapterLabel)
                            .addComponent(chapterNameLabel)
                            .addComponent(chapterMarinesCountLabel)
                    )
                    .addGroup(layout.createParallelGroup()
                            .addComponent(nameField)
                            .addComponent(cordXField)
                            .addComponent(cordYField)
                            .addComponent(healthField)
                            .addComponent(astartesCategoryField)
                            .addComponent(weaponField)
                            .addComponent(meleeWeaponField)
                            .addComponent(chapterNameField)
                            .addComponent(chapterMarinesCountField)
                    ));
        }
        int result = JOptionPane.showOptionDialog(null, panel, resourceBundle.getString("Update"), JOptionPane.YES_OPTION,
                QUESTION_MESSAGE, null, new String[]{resourceBundle.getString("Update")}, resourceBundle.getString("Update"));
        if(result == OK_OPTION){
            SpaceMarine newSpaceMarine = new SpaceMarine(
                    nameField.getText(),
                    new Coordinates(
                            Integer.parseInt(cordXField.getText()),
                            Float.parseFloat(cordYField.getText())
                    ),
                    date,
                    Float.parseFloat(healthField.getText()),
                    (AstartesCategory) astartesCategoryField.getSelectedItem(),
                    (Weapon) weaponField.getSelectedItem(),
                    (MeleeWeapon) meleeWeaponField.getSelectedItem(),
                    new Chapter(
                            chapterNameField.getText(),
                            Integer.parseInt(chapterMarinesCountField.getText())
                    ),
                    user.name()
            );
            if(!newSpaceMarine.validate()) {
                JOptionPane.showMessageDialog(null, resourceBundle.getString("ObjectNotValid"), resourceBundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            Response response = client.sendAndAskResponse(new Request("update", id.toString(), user, newSpaceMarine));
            if(response.getStatus() == ResponseStatus.OK) JOptionPane.showMessageDialog(null, resourceBundle.getString("ObjectUpdated"), resourceBundle.getString("Ok"), JOptionPane.PLAIN_MESSAGE);
            else JOptionPane.showMessageDialog(null, resourceBundle.getString("ObjectNotChanged"), resourceBundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
        }
    }
}

