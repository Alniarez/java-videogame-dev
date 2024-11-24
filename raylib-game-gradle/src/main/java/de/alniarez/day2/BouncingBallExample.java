package de.alniarez.day2;

import com.raylib.Color;
import com.raylib.Rectangle;
import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.core.Screen;

import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.KeyboardKey.*;

enum PaddleMovement {
    Left, Right, Still
}

class Ball {

    final int ballRadius;
    final Vector2 ballPosition;
    final Vector2 ballMovement;
    final Color color;

    protected Ball(Vector2 ballPosition, Vector2 ballMovement, int ballRadius, Color color) {
        this.ballPosition = ballPosition;
        this.ballMovement = ballMovement;
        this.ballRadius = ballRadius;
        this.color = color;
    }

}

public class BouncingBallExample implements IBasicExample {

    Random random = new Random();

    protected Screen screen;
    private final int top = 0;
    private final int left = 0;
    private int right = 0;
    private int bottom = 0;

    private final Rectangle paddle;
    private int paddleSpeed = 280;

    private PaddleMovement paddleMovement;
    private boolean addBall = false;

    private Ball[] balls;

    private long lastTime;
    private double deltaTime;

    public BouncingBallExample(Screen screen) {
        super();

        this.screen = screen;
        this.screen.title("Bounce");
        this.screen.init();

        right = screen.width();
        bottom = screen.height();

        paddle = new Rectangle();
        paddle.width(140);
        paddle.height(20);
        paddle.x((screen.width() - paddle.width()) / 2f);
        paddle.y(bottom - 20 - paddle.height());

        balls = new Ball[]{
                new Ball(new Vector2(20, 20),
                        new Vector2(160, 160),
                        16,
                        DARKGREEN)
        };

    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();

        while (!windowShouldClose()) {
            long currentTime = System.nanoTime();
            // Calculate delta time in seconds
            deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
            lastTime = currentTime;

            input();
            logic();
            render();
        }

        closeWindow();
    }

    private void input() {
        if (isKeyDown(KEY_RIGHT))
            paddleMovement = PaddleMovement.Right;
        else if (isKeyDown(KEY_LEFT))
            paddleMovement = PaddleMovement.Left;
        else
            paddleMovement = PaddleMovement.Still;

        addBall = (isKeyPressed(KEY_ENTER));

    }

    private void logic() {
        movePaddle:
        {
            var paddlePositionX = paddle.getX();
            float movement = 0f;

            if(paddleMovement.equals(PaddleMovement.Still))
                break movePaddle;

            if (paddleMovement.equals(PaddleMovement.Left))
                movement = (float) (deltaTime * -paddleSpeed);
            else if (paddleMovement.equals(PaddleMovement.Right))
                movement = (float) (deltaTime * paddleSpeed);

            paddlePositionX += movement;

            if (paddlePositionX < 0)
                paddlePositionX = 0;
            else if (paddlePositionX > right - paddle.getWidth())
                paddlePositionX = right - paddle.getWidth();

            paddle.setX(paddlePositionX);
        }

        for(var ball : balls)
        {
            // todo: bounce off paddle is bit wonky (can move paddle into ball)
            double x = ball.ballPosition.x() +  ball.ballMovement.x() * deltaTime;
            double y =  ball.ballPosition.y() +  ball.ballMovement.y() * deltaTime;

            // bounce off edges (testing)
            if (x -  ball.ballRadius < left || x +  ball.ballRadius > right)
                ball.ballMovement.x( ball.ballMovement.x() * -1);
            if (y -  ball.ballRadius < top || y +  ball.ballRadius > bottom)
                ball.ballMovement.y( ball.ballMovement.y() * -1);


            var ballPositionAux = new Vector2((float) x, (float) y);
            var bounce = checkPaddleBounce(ballPositionAux,  ball.ballRadius, paddle);
            if (bounce[0])
                // horizontal bounce
                ball.ballMovement.x( ball.ballMovement.x() * -1);
            if (bounce[1])
                // verticalBounce bounce
                ball.ballMovement.y( ball.ballMovement.y() * -1);

            ball.ballPosition.x((float) ( ball.ballPosition.x() +  ball.ballMovement.x() * deltaTime));
            ball.ballPosition.y((float) ( ball.ballPosition.y() +  ball.ballMovement.y() * deltaTime));

        }

        if(addBall)
            balls = addBall(balls);


    }

    private void render() {
        beginDrawing();

        clearBackground(RAYWHITE);

        drawTestGrid();

        // Draw ball
        for(var ball : balls)
            drawCircle((int) ball.ballPosition.x(), (int) ball.ballPosition.y(), ball.ballRadius, ball.color);

        // Draw paddle
        drawRectangle((int) paddle.x(), (int) paddle.y(), (int) paddle.width(), (int) paddle.height(), BLACK);

        drawText(String.format("Time between frames: %.4f ms%n", deltaTime), 20, 20, 10, GRAY);

        endDrawing();
    }


    private void drawTestGrid() {
        int spacing = 50;

        // Draw horizontal
        for (int x = 0; x < screen.width(); x = x + spacing)
            drawLine(x, 0, x, screen.height(), BLUE);

        // Draw vertical
        for (int y = 0; y < screen.width(); y = y + spacing)
            drawLine(0, y, screen.width(), y, BLUE);

        // Draw center
        drawLine(screen.width() / 2, 0, screen.width() / 2, screen.width(), RED);
        drawLine(0, screen.height() / 2, screen.width(), screen.height() / 2, RED);

    }

    private boolean[] checkPaddleBounce(Vector2 ballPosition, float ballRadius, Rectangle rect) {
        boolean horizontalBounce = false;
        boolean verticalBounce = false;

        // Find the closest point on the rectangle to the ball
        float closestX = Math.max(rect.x(), Math.min(ballPosition.x(), rect.x() + rect.width()));
        float closestY = Math.max(rect.y(), Math.min(ballPosition.y(), rect.y() + rect.height()));

        // Calculate the distance between the ball's center and this closest point
        float distanceX = ballPosition.x() - closestX;
        float distanceY = ballPosition.y() - closestY;

        // Check if the ball is colliding with the rectangle
        boolean isColliding = (distanceX * distanceX + distanceY * distanceY) <= (ballRadius * ballRadius);

        if (isColliding) {
            // Determine if the collision is horizontal or vertical
            float overlapX = Math.abs(distanceX);
            float overlapY = Math.abs(distanceY);

            if (overlapX >= overlapY)
                // Horizontal collision (left or right side of the rectangle)
                horizontalBounce = true;

            if (overlapX <= overlapY)
                // Vertical collision (top or bottom side of the rectangle)
                verticalBounce = true;


        }

        return new boolean[]{horizontalBounce, verticalBounce};
    }

    private Ball[] addBall(Ball[] balls) {
        var newBall = new Ball(new Vector2(screen.width()/2f, screen.height()/2f),
                new Vector2(
                        random.nextBoolean() ? 100 + random.nextInt(161) : -200 + random.nextInt(161), random.nextBoolean() ? 160 + random.nextInt(101) : -260 + random.nextInt(101)),
                12 + random.nextInt(11),
                new Color((byte) random.nextInt(256), (byte) random.nextInt(256), (byte) random.nextInt(256), (byte)  255));

        Ball[] newBalls = new Ball[balls.length + 1];
        System.arraycopy(balls, 0, newBalls, 0, balls.length);
        newBalls[balls.length] = newBall;
        return newBalls;
    }

    private Ball[] addBall(Ball[] balls, Ball newBall) {
        Ball[] newBalls = new Ball[balls.length + 1];
        System.arraycopy(balls, 0, newBalls, 0, balls.length);
        newBalls[balls.length] = newBall;
        return newBalls;
    }


}
