package org.example.gui;



import org.example.data.SpaceMarine;
import org.example.dtp.*;
import org.example.utils.Client;
import org.example.gui.action.*;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.*;

import static javax.swing.JOptionPane.*;


public class GuiManager {
    private final Client client;
    private static Locale locale = new Locale("ru");
    private final ClassLoader classLoader = this.getClass().getClassLoader();
    private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GuiLabels", GuiManager.getLocale());
    private final JFrame frame;
    private Panel panel;
    private JTable table = null;
    private StreamTableModel tableModel = null;
    private CartesianPanel cartesianPanel = null;
    private ArrayList<SpaceMarine> tableData = null;
    private ArrayList<SpaceMarine> collection = null;
    private FilterWorker filterWorker = new FilterWorker();
    private Map<JButton, String> buttonsToChangeLocale = new LinkedHashMap<>();
    private User user;

    private final static Color RED_WARNING = Color.decode("#FF4040");
    private final static Color GREEN_OK = Color.decode("#00BD39");

    String[] columnNames = {"id",
            "name",
            "coordinates",
            "creation_date",
            "health",
            "astartes_category",
            "weapon",
            "melee_weapon",
            "chapter_name",
            "chapter_marines_count",
            "owner_login"
    };

    public GuiManager(Client client) {
        this.client = client;

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.frame = new JFrame(resourceBundle.getString("proglab8"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(this::run);

    }

    public GuiManager(Client client, User user) {
        this.client = client;
        this.user = user;
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.frame = new JFrame(resourceBundle.getString("proglab8"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(this::run);

    }

    public void run(){
        panel = new Panel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        if(user == null) this.loginAuth();
        this.tableData = this.getTableDataSpaceMarine();
        this.tableModel = new StreamTableModel(columnNames, tableData.size(), filterWorker);
        this.tableModel.setDataVector(tableData, columnNames);
        this.table = new JTable(tableModel);
        frame.setJMenuBar(this.createMenuBar());

        JButton tableExecute = new JButton(resourceBundle.getString("Table"));
        JButton cartesianExecute = new JButton(resourceBundle.getString("Coordinates"));


        new Timer(500, (i) ->{
            this.timerTrigger();
        }).start();

        // Выбрать столбец для сортировки
        table.getTableHeader().setReorderingAllowed(false);
        table.setDragEnabled(false);
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int column = table.columnAtPoint(point);
                tableModel.performSorting(column);
                table.repaint();
            }
        });
        // Выбрать строку для изменения
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Long id = tableModel.getRow(table.getSelectedRow()).getId();
                new UpdateAction(user, client, GuiManager.this).updateJOptionWorker(id);
            }
        });



        JScrollPane tablePane = new JScrollPane(table);
        this.cartesianPanel = new CartesianPanel(client, user, this);
        JPanel cardPanel = new JPanel();
        //todo
        ImageIcon userIcon = new ImageIcon(new ImageIcon(classLoader.getResource("icons/user.png"))
                .getImage()
                .getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING));
        JLabel userLabel = new JLabel(user.name());
        userLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        userLabel.setIcon(userIcon);
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.add(tablePane, "Table");
        cardPanel.add(cartesianPanel, "Cartesian");

        tableExecute.addActionListener((actionEvent) -> {
            cardLayout.show(cardPanel, "Table");
        });
        cartesianExecute.addActionListener((actionEvent) -> {
            this.cartesianPanel.reanimate();
            cardLayout.show(cardPanel, "Cartesian");
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(cardPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tableExecute)
                                .addComponent(cartesianExecute)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(userLabel)
                                .addGap(5))));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(cardPanel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(tableExecute)
                        .addComponent(cartesianExecute)
                        .addComponent(userLabel)
                        .addGap(5)));
        frame.add(panel);
        frame.setVisible(true);
    }

    public ArrayList<SpaceMarine> getTableDataSpaceMarine(){
        Response response = client.sendAndAskResponse(new Request("show", "", user, GuiManager.getLocale()));
        if(response.getStatus() != ResponseStatus.OK) return null;
        this.collection = new ArrayList<>(response.getCollection());
        return new ArrayList<>(response.getCollection());
    }

    private JMenuBar createMenuBar(){
        int iconSize = 40;
//todo change объявление переменных
        JMenuBar menuBar = new JMenuBar();
        JMenu actions = new JMenu(resourceBundle.getString("Actions"));
        JMenuItem add = new JMenuItem(resourceBundle.getString("Add"));
        JMenuItem clear = new JMenuItem(resourceBundle.getString("Clear"));
        JMenuItem executeScript = new JMenuItem(resourceBundle.getString("ExecuteScript"));
        JMenuItem exit = new JMenuItem(resourceBundle.getString("Exit"));
        JMenuItem info = new JMenuItem(resourceBundle.getString("Info"));
        JMenuItem printFieldDescendingWeapon = new JMenuItem(resourceBundle.getString("PrintFieldDescendingWeapon"));
        JMenuItem printUniqueMeleeWeapon = new JMenuItem(resourceBundle.getString("PrintUniqueMeleeWeapon"));
        JMenuItem remove = new JMenuItem(resourceBundle.getString("Remove"));
        JMenuItem removeGreater = new JMenuItem(resourceBundle.getString("RemoveGreater"));
        JMenuItem sort = new JMenuItem(resourceBundle.getString("Sort"));
        JMenuItem shuffle = new JMenuItem(resourceBundle.getString("Shuffle"));
        JMenuItem update = new JMenuItem(resourceBundle.getString("Update"));
        JMenuItem language = new JMenuItem(resourceBundle.getString("Language"));

        add.addActionListener(new AddAction(user, client, this));
        clear.addActionListener(new ClearAction(user, client, this));
        executeScript.addActionListener(new ExecuteScriptAction(user, client, this));
        exit.addActionListener(new ExitAction(user, client, this));
        info.addActionListener(new InfoAction(user, client, this));
        printFieldDescendingWeapon.addActionListener(new PrintFieldDescendingWeaponAction(user, client, this));
        printUniqueMeleeWeapon.addActionListener(new PrintUniqueMeleeWeaponAction(user, client, this));
        remove.addActionListener(new RemoveAction(user, client, this));
        removeGreater.addActionListener(new RemoveGreaterAction(user, client, this));
        sort.addActionListener(new SortAction(user, client, this));
        shuffle.addActionListener(new ShuffleAction(user, client, this));
        update.addActionListener(new UpdateAction(user, client, this));
        language.addActionListener(new ChangeLanguageAction(user, client, this));

        add.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/add.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        clear.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/clear.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        executeScript.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/execute.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        exit.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/exit.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        info.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/info.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        printFieldDescendingWeapon.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/descending_weapon.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        printUniqueMeleeWeapon.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/unique_melee_weapon.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        remove.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/remove.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        removeGreater.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/remove_greater.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        sort.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/sort.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        shuffle.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/shuffle.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        update.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/update.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));
        language.setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("icons/language.png"))
                .getImage()
                .getScaledInstance(iconSize, iconSize, Image.SCALE_AREA_AVERAGING)));


        actions.add(add);
        actions.addSeparator();
        actions.add(update);
        actions.addSeparator();
        actions.add(remove);
        actions.add(removeGreater);
        actions.add(clear);
        actions.addSeparator();
        actions.add(printFieldDescendingWeapon);
        actions.add(printUniqueMeleeWeapon);
        actions.add(info);
        actions.addSeparator();
        actions.add(language);
        actions.addSeparator();
        actions.add(executeScript);
        actions.add(exit);

        menuBar.add(actions);

        JMenuItem clearFilters = new JMenuItem(resourceBundle.getString("ClearFilter"));
        JMenuItem idFilter = new JMenuItem("id");
        JMenuItem nameFilter = new JMenuItem("name");
        JMenuItem cordFilter = new JMenuItem("coordinates");
        JMenuItem creationDateFilter = new JMenuItem("creation_date");
        JMenuItem healthFilter = new JMenuItem("health");
        JMenuItem astartesCategoryFilter = new JMenuItem("astartes_category");
        JMenuItem weaponFilter = new JMenuItem("weapon");
        JMenuItem meleeWeaponFilter = new JMenuItem("melee_weapon");
        JMenuItem chapterNameFilter = new JMenuItem("chapter_name");
        JMenuItem chapterMarinesCountFilter = new JMenuItem("chapter_marines_count");
        JMenuItem ownerLoginFilter = new JMenuItem("owner_login");

        clearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterWorker.clearPredicates();
                tableModel.performFiltration();
                table.repaint();
            }
        });
        idFilter.addActionListener(new FilterListener(0, tableModel, table, filterWorker));
        nameFilter.addActionListener(new FilterListener(1, tableModel, table, filterWorker));
        cordFilter.addActionListener(new FilterListener(2, tableModel, table, filterWorker));
        creationDateFilter.addActionListener(new FilterListener(3, tableModel, table, filterWorker));
        healthFilter.addActionListener(new FilterListener(4, tableModel, table, filterWorker));
        astartesCategoryFilter.addActionListener(new FilterListener(5, tableModel, table, filterWorker));
        weaponFilter.addActionListener(new FilterListener(6, tableModel, table, filterWorker));
        meleeWeaponFilter.addActionListener(new FilterListener(7, tableModel, table, filterWorker));
        chapterNameFilter.addActionListener(new FilterListener(8, tableModel, table, filterWorker));
        chapterMarinesCountFilter.addActionListener(new FilterListener(9, tableModel, table, filterWorker));
        ownerLoginFilter.addActionListener(new FilterListener(10, tableModel, table, filterWorker));

        JMenu filters = new JMenu(resourceBundle.getString("Filters"));

        filters.add(clearFilters);
        filters.add(idFilter);
        filters.add(nameFilter);
        filters.add(cordFilter);
        filters.add(creationDateFilter);
        filters.add(healthFilter);
        filters.add(astartesCategoryFilter);
        filters.add(weaponFilter);
        filters.add(meleeWeaponFilter);
        filters.add(chapterNameFilter);
        filters.add(chapterMarinesCountFilter);
        filters.add(ownerLoginFilter);

        menuBar.add(filters);
        return menuBar;
    }

    public void loginAuth(){
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JLabel loginTextLabel = new JLabel(resourceBundle.getString("WriteLogin"));
        JTextField loginField = new JTextField();
        JLabel passwordTextLabel = new JLabel(resourceBundle.getString("EnterPass"));
        JPasswordField passwordField = new JPasswordField();
        JLabel errorLabel = new JLabel("");
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(loginTextLabel)
                        .addComponent(passwordTextLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(loginField)
                        .addComponent(passwordField)
                        .addComponent(errorLabel)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginTextLabel)
                        .addComponent(loginField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordTextLabel)
                        .addComponent(passwordField))
                .addComponent(errorLabel));
        while(true) {
            int result = JOptionPane.showOptionDialog(null, panel, resourceBundle.getString("Login"), JOptionPane.YES_NO_OPTION,
                    QUESTION_MESSAGE, null, new String[]{resourceBundle.getString("Login"), resourceBundle.getString("Register")}, resourceBundle.getString("Login"));
            if (result == OK_OPTION) {
                if (!checkFields(loginField, passwordField, errorLabel)) continue;
                Response response = client.sendAndAskResponse(
                        new Request(
                                "ping",
                                "",
                                new User(loginField.getText(), String.valueOf(passwordField.getPassword())),
                                GuiManager.getLocale()));
                if (response.getStatus() == ResponseStatus.OK) {
                    errorLabel.setText(resourceBundle.getString("LoginAcc"));
                    errorLabel.setForeground(GREEN_OK);
                    this.user = new User(loginField.getText(), String.valueOf(passwordField.getPassword()));
                    return;
                } else {
                    errorLabel.setText(resourceBundle.getString("LoginNotAcc"));
                    errorLabel.setForeground(RED_WARNING);
                }
            } else if (result == NO_OPTION){
                if (!checkFields(loginField, passwordField, errorLabel)) continue;
                Response response = client.sendAndAskResponse(
                        new Request(
                                "register",
                                "",
                                new User(loginField.getText(), String.valueOf(passwordField.getPassword())),
                                GuiManager.getLocale()));
                if (response.getStatus() == ResponseStatus.OK) {
                    errorLabel.setText(resourceBundle.getString("RegAcc"));
                    errorLabel.setForeground(GREEN_OK);
                    this.user = new User(loginField.getText(), String.valueOf(passwordField.getPassword()));
                    return;
                } else {
                    errorLabel.setText(resourceBundle.getString("RegNotAcc"));
                    errorLabel.setForeground(RED_WARNING);
                }
            } else if (result == CLOSED_OPTION) {
                System.exit(666);
            }
        }
    }

    private boolean checkFields(JTextField loginField, JPasswordField passwordField, JLabel errorLabel){
        if(loginField.getText().isEmpty()) {
            errorLabel.setText(resourceBundle.getString("LoginNotNull"));
            errorLabel.setForeground(RED_WARNING);
            return false;
        } else if(String.valueOf(passwordField.getPassword()).isEmpty()){
            errorLabel.setText(resourceBundle.getString("PassNotNull"));
            errorLabel.setForeground(RED_WARNING);
            return false;
        }
        return true;
    }

    public Collection<SpaceMarine> getCollection() {
        return collection;
    }

    public static Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        GuiManager.locale = locale;
        Locale.setDefault(locale);
        ResourceBundle.clearCache();
        resourceBundle = ResourceBundle.getBundle("GuiLabels", locale);
        dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
        this.buttonsToChangeLocale.forEach((i, j) -> i.setText(resourceBundle.getString(j)));
        this.tableData = this.getTableDataSpaceMarine();
        this.tableModel.setDataVector(this.tableData, columnNames);
        this.tableModel.fireTableDataChanged();
        this.frame.remove(panel);
        this.frame.setTitle(resourceBundle.getString("LabWork8"));
        this.run();
    }

    public void repaintNoAnimation(){
        ArrayList<SpaceMarine> newTableData = this.getTableDataSpaceMarine();
        this.tableData = newTableData;
        this.tableModel.setDataVector(this.tableData, columnNames);
        this.tableModel.performFiltration();
        this.table.repaint();
        this.tableModel.fireTableDataChanged();
//        this.cartesianPanel.updateUserColors();
        this.cartesianPanel.reanimate(100);
    }

    public void timerTrigger(){
        ArrayList<SpaceMarine> newTableData = this.getTableDataSpaceMarine();
        if(!(this.tableData.equals(newTableData))) {
            this.tableData = newTableData;
            this.tableModel.setDataVector(this.tableData, columnNames);
            this.tableModel.performFiltration();
            this.table.repaint();
            this.tableModel.fireTableDataChanged();
            this.cartesianPanel.updateUserColors();
            this.cartesianPanel.reanimate();
        }
    }
}

