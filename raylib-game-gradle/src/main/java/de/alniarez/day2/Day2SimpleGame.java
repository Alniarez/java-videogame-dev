package de.alniarez.day2;

import com.raylib.Color;
import de.alniarez.core.GenericGameLoop;
import de.alniarez.core.Screen;

import java.util.Random;

import static com.raylib.Raylib.*;

/**
 * Just trying to make something on rayblib not overthinking any scene or gameloop or anything like that
 */
public class Day2SimpleGame extends GenericGameLoop {

    Screen screen;
    Day2SimpleGameState state;
    Random random;

    public Day2SimpleGame(Screen screen) {
        super();
        this.screen = screen;
        state = new Day2SimpleGameState();
        random = new Random();

        for(int x = 0; x < state.grid.length; x++) {
            for(int y = 0; y < state.grid[x].length; y++) {
                state.grid[x][y] = Math.random() > 0.5 ? 1 : 0;
            }
        }

        state.cellWidth = (screen.width() / state.grid.length);
        state.cellHeight = (screen.height() / state.grid[0].length);

    }

    @Override
    protected void logicUpdate() {
        var roll = random.nextInt(100);

        if(roll == 0)
            state.grid[random.nextInt(state.grid.length)][random.nextInt(state.grid[0].length)] = 2;
        else if(roll < 20) {
            var x1 = random.nextInt(state.grid.length);
            var y1 = random.nextInt(state.grid[x1].length);

            var x2 = random.nextInt(state.grid.length);
            var y2 = random.nextInt(state.grid[x2].length);

            var swap = state.grid[x1][y1];

            state.grid[x1][y1] = state.grid[x2][y2];
            state.grid[x2][y2] = swap;
        }

    }

    @Override
    protected void render() {
        beginDrawing();

        for(int x = 0; x < state.grid.length; x++) {
            for(int y = 0; y < state.grid[x].length; y++) {
                drawRectangle(x * (state.cellWidth ), y * (state.cellHeight ), state.cellWidth, state.cellHeight, color(x,y));
            }
        }

        endDrawing();
    }

    private Color color(int x, int y) {
        return switch (state.grid[x][y]) {
            case 0 -> GREEN;
            case 1 -> BLUE;
            case 2 -> RED;
            default -> WHITE;
        };

    }

    void swap(int[][] array, int row1, int col1, int row2, int col2) {
        // Ensure indices are within bounds
        if (row1 < 0 || row1 >= array.length || row2 < 0 || row2 >= array.length ||
                col1 < 0 || col1 >= array[row1].length || col2 < 0 || col2 >= array[row2].length) {
            throw new IllegalArgumentException("Indices are out of bounds");
        }

        // Swap the values
        int temp = array[row1][col1];
        array[row1][col1] = array[row2][col2];
        array[row2][col2] = temp;
    }
}
