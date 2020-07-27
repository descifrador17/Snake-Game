import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private int[] snakeXLength;
    private int[] snakeYLength;

    private int snakeLength;

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private ImageIcon headRight;
    private ImageIcon headLeft;
    private ImageIcon headUp;
    private ImageIcon headDown;
    private ImageIcon snakeBody;
    private ImageIcon food;
    private ImageIcon titleImage;

    private final Timer timer;

    private final Random random;

    private int foodXPosition;
    private int foodYPosition;

    private int score = 0;

    private Graphics graphics;

    private boolean newGame = true;

    public GamePanel(){

        random = new Random();

        putFood();

        initializeKeys();

        loadAssets();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        int delay = 100;
        timer = new Timer(delay,this);
        timer.start();

    }

    public void paint(Graphics g){

        graphics = g;

        if(newGame){
            initializeSnake();
            newGame = false;
        }

        loadLayout();

        if(foodXPosition == snakeXLength[0] && foodYPosition == snakeYLength[0]){
            snakeLength++;
            score++;

            putFood();
        }

        food.paintIcon(this,graphics,foodXPosition,foodYPosition);


        for (int i = 1; i <snakeLength ; i++) {
            if(snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]){
                gameOver();
            }
        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(right){
            for (int i = snakeLength-1; i>=0 ; i--) {
                snakeYLength[i+1] = snakeYLength[i];
            }
            for (int i = snakeLength-1; i>=0 ; i--) {
                if(i == 0){
                    snakeXLength[i] = snakeXLength[i] + 25;
                }
                else {
                    snakeXLength[i] = snakeXLength[i-1];
                }
                if(snakeXLength[i]>950){
                    snakeXLength[i] = 25;
                }
            }

            repaint();
        }

        if(left){
            for (int i = snakeLength-1; i>=0 ; i--) {
                snakeYLength[i+1] = snakeYLength[i];
            }
            for (int i = snakeLength-1; i>=0 ; i--) {
                if(i == 0){
                    snakeXLength[i] = snakeXLength[i] - 25;
                }
                else {
                    snakeXLength[i] = snakeXLength[i-1];
                }
                if(snakeXLength[i]<25){
                    snakeXLength[i] = 950;
                }
            }

            repaint();
        }

        if(up){
            for (int i = snakeLength-1; i>=0 ; i--) {
                snakeXLength[i+1] = snakeXLength[i];
            }
            for (int i = snakeLength-1; i>=0 ; i--) {
                if(i == 0){
                    snakeYLength[i] = snakeYLength[i] - 25;
                }
                else {
                    snakeYLength[i] = snakeYLength[i-1];
                }
                if(snakeYLength[i]<75){
                    snakeYLength[i] = 825;
                }
            }

            repaint();
        }

        if(down){
            for (int i = snakeLength-1; i>=0 ; i--) {
                snakeXLength[i+1] = snakeXLength[i];
            }
            for (int i = snakeLength-1; i>=0 ; i--) {
                if(i == 0){
                    snakeYLength[i] = snakeYLength[i] + 25;
                }
                else {
                    snakeYLength[i] = snakeYLength[i-1];
                }
                if(snakeYLength[i]>825){
                    snakeYLength[i] = 75;
                }
            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            score = 0;
            snakeLength = 3;
            newGame = true;
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = !left;
            up = false;
            down = false;

        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = !right;
            up = false;
            down = false;

        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up = !down;
            left = false;
            right = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down = !up;
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void gameOver(){
        initializeKeys();

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial",Font.BOLD,50));
        graphics.drawString("Game Over!!",300,500);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial",Font.BOLD,25));
        graphics.drawString("Press SpaceBar to Restart",275,550);

    }

    private void putFood(){
        foodXPosition = random.nextInt(33)*25+25;
        foodYPosition = random.nextInt(27)*25+75;
    }

    private void initializeKeys(){
        right = false;
        left = false;
        up = false;
        down = false;
    }

    private void initializeSnake() {
        snakeXLength = new int[900];
        snakeYLength = new int[900];
        snakeLength = 3;

        snakeXLength[2] = 50;
        snakeXLength[1] = 75;
        snakeXLength[0] = 100;

        snakeYLength[2] = 100;
        snakeYLength[1] = 100;
        snakeYLength[0] = 100;
    }

    private void loadAssets() {
        headRight = new ImageIcon(GamePanel.class.getResource("/assets/head_right.png"));
        headLeft = new ImageIcon(GamePanel.class.getResource("/assets/head_left.png"));
        headUp = new ImageIcon(GamePanel.class.getResource("/assets/head_top.png"));
        headDown = new ImageIcon(GamePanel.class.getResource("/assets/head_down.png"));
        snakeBody = new ImageIcon(GamePanel.class.getResource("assets/snake_body.png"));
        food = new ImageIcon(GamePanel.class.getResource("assets/food.png"));
        titleImage = new ImageIcon(GamePanel.class.getResource("/assets/title.png"));
    }

    private void loadLayout() {
        graphics.setColor((Color.WHITE));
        graphics.fillRect(25,10,945,60);

        titleImage.paintIcon(this,graphics,350,20);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(25,75,945,770);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial",Font.BOLD,14));
        graphics.drawString("Score : "+score,780,30);

        headRight.paintIcon(this,graphics,snakeXLength[0],snakeYLength[0]);
        for (int i = 0; i <snakeLength ; i++) {

            if(i==0 && right){
                headRight.paintIcon(this,graphics,snakeXLength[i],snakeYLength[i]);
            }
            if(i==0 && left){
                headLeft.paintIcon(this,graphics,snakeXLength[i],snakeYLength[i]);
            }
            if(i==0 && down){
                headDown.paintIcon(this,graphics,snakeXLength[i],snakeYLength[i]);
            }
            if(i==0 && up){
                headUp.paintIcon(this,graphics,snakeXLength[i],snakeYLength[i]);
            }
            if(i!=0){
                snakeBody.paintIcon(this,graphics,snakeXLength[i],snakeYLength[i]);
            }
        }
    }
}
