//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable {

    Vehicle Car1;
    Vehicle Car2;
    Vehicle Ball;

    boolean isColliding1 = false;
    boolean isColliding2 = false;
    boolean isColliding3 = false;

    Image BackgroundPic;

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 1000;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game
        Car1 = new Vehicle(0,400,4,1,150,100);
        Car1.pic = Toolkit.getDefaultToolkit().getImage("Car1.png");

        Car2 = new Vehicle(850,400,-4,1,150,100);
        Car2.pic = Toolkit.getDefaultToolkit().getImage("Car2.png");

        Ball = new Vehicle(450,400,1,1,100,100);
        Ball.pic = Toolkit.getDefaultToolkit().getImage("Ball.png");

        BackgroundPic = Toolkit.getDefaultToolkit().getImage("Field.png");

    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            Collide();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }

    public void moveThings() {
        //call the move() code for each object
        Car1.move();
        Car2.move();
        Ball.move();
    }

    public void Collide(){
        if(Car1.hitbox.intersects(Ball.hitbox) && Car1.dx > 0 && isColliding1 == false){
            Ball.dx = -Ball.dx;
            Ball.dy = -Ball.dy;
            isColliding1 = true;
        }
        if(Car1.hitbox.intersects(Car2.hitbox) && isColliding2 == false){
            Car1.dx = -Car1.dx;
            Car1.dy = -Car1.dy;
            Car2.dx = -Car2.dx;
            Car2.dy = -Car2.dy;
            isColliding2 = true;
        }
        if(Car2.hitbox.intersects(Ball.hitbox) && Car2.dx > 0 && isColliding3 == false){
            Ball.dx = -Ball.dx;
            Ball.dy = -Ball.dy;
            isColliding3 = true;
        }

        if(Car1.hitbox.intersects(Ball.hitbox) == false){
            isColliding1 = false;
        }
        if(Car1.hitbox.intersects(Ball.hitbox) == false){
            isColliding2 = false;
        }
        if(Car2.hitbox.intersects(Car2.hitbox) == false){
            isColliding3 = false;
        }
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(BackgroundPic, 0,0,WIDTH,HEIGHT,null);
        g.drawImage(Car1.pic, Car1.xpos, Car1.ypos, Car1.width, Car1.height, null);
        g.drawImage(Car2.pic, Car2.xpos, Car2.ypos, Car2.width, Car2.height, null);
        g.drawImage(Ball.pic, Ball.xpos, Ball.ypos, Ball.width, Ball.height, null);
        //draw the images
        g.drawRect(Car1.hitbox.x,Car1.hitbox.y,Car1.hitbox.width,Car1.hitbox.height);
        g.drawRect(Car2.hitbox.x,Car2.hitbox.y,Car2.hitbox.width,Car2.hitbox.height);

        g.dispose();
        bufferStrategy.show();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
        /*JFrame jf = new JFrame();
        jf.setSize(1920,1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);*/

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}