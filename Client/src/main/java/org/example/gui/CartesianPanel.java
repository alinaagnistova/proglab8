package org.example.gui;

import org.example.dtp.*;
import org.example.gui.action.UpdateAction;
import org.example.data.*;
import org.example.utils.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class CartesianPanel extends JPanel implements ActionListener {
    private Client client;
    private User user;
    private GuiManager guiManager;
    private LinkedHashMap<Rectangle, Long> rectangles = new LinkedHashMap<>();
    private Timer timer;
    private Map<String, Color> users;
    private int step;
    private Collection<SpaceMarine> collection;

    private float maxCordX;
    private Float maxCordY;
    private BufferedImage img = null;
    private boolean isDragging = false;
    private boolean skip_animation = false;


    {
        try {
            this.img = ImageIO.read(getClass().getClassLoader().getResource("icons/rayan.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CartesianPanel(Client client, User user, GuiManager guiManager){
        super();
        this.client = client;
        this.user = user;
        this.guiManager = guiManager;
        this.step = 0;
        this.timer = new Timer(1, this);
        timer.start();
        updateUserColors();
        // Изменение по дабл клику
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() != 2) return;
                Rectangle toClick;
                try {
                    toClick = rectangles.keySet().stream()
                            .filter(r -> r.contains(e.getPoint()))
                            .sorted(Comparator.comparing(Rectangle::getX).reversed())
                            .toList().get(0);
                } catch (ArrayIndexOutOfBoundsException k) {
                    return;
                }
                Long id = rectangles.get(toClick);
                new UpdateAction(user, client, guiManager).updateJOptionWorker(id);
            }
        });
    }

    public void updateUserColors() {
        Random random = new Random();
        Response response = client.sendAndAskResponse(new Request("show", "", user));
        this.users = response.getCollection().stream()
                .map(SpaceMarine::getUserLogin)
                .distinct()
                .collect(Collectors.toMap(
                        s -> s, s -> {
                            int red = random.nextInt(25) * 10;
                            int green = random.nextInt(25) * 10;
                            int blue = random.nextInt(25) * 10;
                            return new Color(red, green, blue);
                        }));
        float delta = 0.2F;
        while(response.getCollection().stream().map(SpaceMarine::getCoordinates).distinct().count() < response.getCollection().size()){
            for(SpaceMarine spaceMarine : response.getCollection()){
                if(response.getCollection().stream()
                        .anyMatch((i) -> i.getCoordinates().equals(spaceMarine.getCoordinates())
                                && !i.getId().equals(spaceMarine.getId()))){
                    spaceMarine.getCoordinates().setX((int) (spaceMarine.getCoordinates().getX() + delta));
                    spaceMarine.getCoordinates().setY(spaceMarine.getCoordinates().getY() + delta);
                    break;
                }
            }
        }
        this.collection = response.getCollection();
        this.maxCordX = this.collection.stream()
                .map(SpaceMarine::getCoordinates)
                .map(Coordinates::getX)
                .map(Math::abs)
                .max(Integer::compareTo)
                .orElse(0);
        this.maxCordY = this.collection.stream()
                .map(SpaceMarine::getCoordinates)
                .map(Coordinates::getY)
                .map(Math::abs)
                .max(Float::compareTo)
                .orElse((float) 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setFont(new Font("Tahoma", Font.BOLD, 45));

        int width = getWidth();
        int height = getHeight();

        // Draw x-axis
        g2.drawLine(0, height / 2, width, height / 2);

        // Draw y-axis
        g2.drawLine(width / 2, 0, width / 2, height);

        // Draw arrows
        g2.drawLine(width - 10, height / 2 - 5, width, height / 2);
        g2.drawLine(width - 10, height / 2 + 5, width, height / 2);
        g2.drawLine(width / 2 - 5, 10, width / 2, 0);
        g2.drawLine(width / 2 + 5, 10, width / 2, 0);
        this.paintRectangles(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(step == 100) timer.stop();
        else{
            step += 2;
            step = Math.min(step, 100);
            repaint();
        }
    }

    private void paintRectangles(Graphics2D g2) {
        int width = getWidth();
        int halfWidth = width / 2;
        int height = getHeight();
        int halfHeight = height / 2;
        int elementWidth = 130;
        int elementHeight = 130;
        if (skip_animation == true) {
            this.step = 100;
            this.skip_animation = false;
        }
        if (step == 100) this.rectangles = new LinkedHashMap<>();
        this.collection.stream().sorted(SpaceMarine::compareTo).forEach(studyGroup -> {
            int dx1 = (int) ((halfWidth + (studyGroup.getCoordinates().getX() / maxCordX * (halfWidth - elementWidth))));
            int dx2 = (int) ((halfHeight + (studyGroup.getCoordinates().getY() / maxCordY * (halfHeight - elementHeight))));
            if (step == 100) {
                this.rectangles.put(new Rectangle(dx1 - elementWidth / 2 - 1,
                        dx2 - elementHeight / 2 - 1,
                        elementWidth + 2,
                        elementHeight + 2), studyGroup.getId());
            }
            Graphics2D g2R = (Graphics2D) g2.create();
            g2R.rotate(Math.toRadians(step * 3.6), dx1, dx2);
            //Image
            g2R.drawImage(img,
                    dx1 - elementWidth / 2,
                    dx2 - elementHeight / 2,
                    dx1 + elementWidth / 2,
                    dx2 + elementHeight / 2,
                    0,
                    0,
                    img.getWidth(),
                    img.getHeight(),
                    null
            );
            //Border
            g2R.setColor(users.get(studyGroup.getUserLogin()));
            g2R.drawRect(dx1 - elementWidth / 2 - 1,
                    dx2 - elementHeight / 2 - 1,
                    elementWidth + 2,
                    elementHeight + 2);
            g2R.setColor(Color.WHITE);
            //Numbers
            g2R.drawString(studyGroup.getId().toString(),
                    dx1 - elementWidth / 4,
                    dx2 + elementHeight / 4
            );
        });
    }

    public void reanimate(){
        this.step = 0;
        this.timer.start();
    }

    public void reanimate(int step){
        this.step = 100;
        repaint();
    }

}
