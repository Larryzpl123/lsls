import java.awt.*; //java draw

import java.util.*; //java system

import java.util.concurrent.CompletableFuture; //time pause function

import java.util.concurrent.ExecutionException; //time pause

public class FlowerAnimation { //1 - main class function (1/3)

    public static void main(String[] args) { //void main

        Frame frame = new Frame("Flower Animation"); //fraim name

        DrawingCanvas canvas = new DrawingCanvas(); //canvas

        frame.setSize(800, 800); //size 800*800

        frame.add(canvas); //add canvas to fram

        frame.setVisible(true); //visible

        frame.addWindowListener(new java.awt.event.WindowAdapter() { //window

            public void windowClosing(java.awt.event.WindowEvent e) {

                System.exit(0); //exit window

            }

        });



        // Animation thread

        new Thread(() -> {

            while (true) {

                canvas.updatePetals(); //keep updating

                canvas.repaint(); //repaint for animation effect

                try { //wait function smoother here, old code turn to comments

                    Thread.sleep(50); //slepp

                } catch (InterruptedException ex) {

                    ex.printStackTrace();

                }

            }

        }).start(); //start

    }

}



class DrawingCanvas extends Canvas { //2 - drawing class canvas (2/3)

    private java.util.List<Petal> petals = Collections.synchronizedList(new ArrayList<>());



    @Override

    public void paint(Graphics g) {

        // Draw background

        g.setColor(new Color(135, 206, 235)); // Sky blue

        g.fillRect(0, 0, 800, 500);

        // Set a transparent green color for the mountains
        Color mountainColor = new Color(0, 255, 127, 150); // 青绿 with alpha for transparency
        g.setColor(mountainColor);
        
        // Create the mountain shape
        int[] xPoints = {0, 100, 200, 350, 500, 550, 600, 800, 800, 0};
        int[] yPoints = {100, 400, 150, 400, 450, 350, 200, 0, 500, 800};
        g.fillPolygon(xPoints, yPoints, xPoints.length);

        g.setColor(new Color(34, 139, 34)); // Forest green

        g.fillRect(0, 500, 800, 400);

        int sunX = 700; // X position for the sun
        int sunY = 100; // Y position for the sun
        int radius = 32; // Radius for the sun polygon
        int spikes = 16; // Number of spikes
    
            // Draw the spikes
        g.setColor(Color.YELLOW);
        for (int i = 0; i < spikes; i++) {
            double angle = 2 * Math.PI / spikes * i;
            int x1 = (int) (sunX + Math.cos(angle) * (radius + 20)); // Outer point
            int y1 = (int) (sunY + Math.sin(angle) * (radius + 20));
            int x2 = (int) (sunX + Math.cos(angle) * radius); // Inner point
            int y2 = (int) (sunY + Math.sin(angle) * radius);
            g.drawLine(x1, y1, x2, y2);
        }
    
            // Draw the center of the sun (32-sided polygon)
        g.setColor(Color.RED);
        int[] xxxPoints = new int[32];
        int[] yyyPoints = new int[32];
        for (int i = 0; i < 32; i++) {
            double angle = 2 * Math.PI / 32 * i;
            xxxPoints[i] = (int) (sunX + Math.cos(angle) * radius);
            yyyPoints[i] = (int) (sunY + Math.sin(angle) * radius);
        }
        g.fillPolygon(xxxPoints, yyyPoints, xxxPoints.length);

        // Draw static elements

        drawTreeHouse(g);

        drawFlowerTree(g);Flower



        // Draw petals

        g.setColor(new Color(255, 71, 76)); // Flower red

        synchronized (petals) {

            for (Petal p : petals) {

                g.fillOval((int) p.x, (int) p.y, 8, 8);

            }

        }

    }



    private void drawTreeHouse(Graphics g) {

        // Main structure

        g.setColor(new Color(139, 69, 19)); // Brown

        g.fillRect(300, 200, 200, 200);

        g.setColor(new Color(1, 1, 1)); //black

        g.drawRect(300, 200, 200, 200);



        // Roof

        int[] xPoints = {250, 550, 400};

        int[] yPoints = {200, 200, 120};

        g.setColor(new Color(178, 34, 34)); // Firebrick

        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(new Color(1, 1, 1)); //outline

        g.drawPolygon(xPoints, yPoints, 3); 

        // Windows

        g.setColor(new Color(135, 206, 250)); // Light sky blue

        g.fillRect(320, 250, 50, 50);

        g.fillRect(430, 250, 50, 50);

        g.setColor(new Color(1, 1, 1)); // black

        g.drawRect(320, 250, 50, 50);

        g.drawRect(430, 250, 50, 50);

        // door
        g.setColor(new Color(1, 1, 1));

        g.drawRect(374, 324, 51, 76);

        g.setColor(new Color(130, 94, 92));

        g.fillRect(375, 325, 50, 75);

        // door handle

        g.setColor(new Color(255, 255, 255));

        g.drawOval(409, 374, 12, 12);

        g.setColor(new Color(70, 44, 42));

        g.fillOval(410, 375, 10, 10);

        // windows lines

        g.drawLine(345, 250, 345, 300);

        g.drawLine(320, 275, 370, 275);

        g.drawLine(430, 275, 480, 275);

        g.drawLine(455, 250, 455, 300);

    }



    private void drawFlowerTree(Graphics g) {

        // Trunk

        g.setColor(new Color(101, 67, 33)); // Wood brown

        g.fillRect(380, 400, 40, 300);



        // Branches

        g.drawLine(400, 450, 300, 500);

        g.drawLine(400, 450, 500, 500);

        g.drawLine(400, 500, 350, 550);

        g.drawLine(400, 500, 450, 550);



        // Blossoms

        g.setColor(new Color(255, 71, 76)); // Flower red

        drawBlossomCluster(g, 280, 490);

        drawBlossomCluster(g, 480, 490);

        drawBlossomCluster(g, 340, 540);

        drawBlossomCluster(g, 440, 540);

		int max1 = 550; //550

        int min1 = 250; //250

        int max2 = 550; //550

        int min2 = 400; //420

        int range1 = max1 - min1 + 1;

        int range2 = max2 - min2 + 1;

        // generate random numbers within 1 to 100
        for (int i = 0; i < 200; i++) {

            int rand = (int)(Math.random() * range1) + min1;

			int rand2 = (int)(Math.random() * range2) + min2;

			drawBlossomCluster(g, rand, rand2);

	}

    }



    private void drawBlossomCluster(Graphics g, int x, int y) {

        for (int i = 0; i < 5; i++) {


            int offsetX = (int) (Math.random() * 20 - 10);

            int offsetY = (int) (Math.random() * 20 - 10);

            g.fillOval(x + offsetX, y + offsetY, 12, 12);

        }

    }



    public void updatePetals() {

        synchronized (petals) {

            // Remove petals that fall out of screen

            petals.removeIf(p -> p.y > 800);



            // Update existing petals

            for (Petal p : petals) {

				//try {
				    //Thread.sleep(5); // Pause
				//} catch (InterruptedException e) {
				    // Handle the exception if the sleep is interrupted
				    //e.printStackTrace();
				    //}

                p.update();

            }



            // Add new petals

            if (Math.random() < 0.2) {

				int min3 = 222;
				int max3 = 555;
				int range3 = max3 - min3 + 1;

				int rand3 = (int)(Math.random() * range3) + min3;

                petals.add(new Petal(rand3, 400 + Math.random() * 200));

            }

        }

    }



    // Double buffering to prevent flickering

    private Image offScreenBuffer;

    private Graphics offScreenGraphics;



    @Override

    public void update(Graphics g) {

        if (offScreenBuffer == null) {

            offScreenBuffer = createImage(getWidth(), getHeight());

            offScreenGraphics = offScreenBuffer.getGraphics();

        }



        // Clear offscreen buffer

        offScreenGraphics.setColor(getBackground());

        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());



        // Draw to offscreen buffer

        paint(offScreenGraphics);



        // Draw offscreen buffer to screen

        g.drawImage(offScreenBuffer, 0, 0, this);

    }

}

//3 - Class Petal (3/3)

class Petal {

    double x, y;

    double angle;

    double speedY;

    double speedX;



    public Petal(double x, double y) {

        this.x = x;

        this.y = y;

        this.angle = Math.random() * 2 * Math.PI;

        this.speedY = 2 + Math.random() * 3;

        this.speedX = 1.5;

    }



    public void update() {

        y += speedY;

        x += Math.cos(angle) * speedX;

        angle += 0.1;

    }

}