import java.lang.Thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class GameOfLife
{
    private Simulation simulation;
    private Renderer renderer;

    private static boolean isRunning = false;

    public GameOfLife(final int simulationWidth, final int simulationHeight)
    {
        renderer = new Renderer();
    }

    public void start()
    {
        // load starting state from file
        // idea: prompt user to enter filename

        isRunning = true;

        loadStartingState();

        gameLoop();
    }

    private void loadStartingState()
    {
        // enter filename
        // first 2 are simulation dimensions
        // next each line is "x y state"
        // eg, we want cell at (2,3) to be alive
        // 2 3 1
        // game of life cells only have 2 states, so if we default initialize
        // every cell to "dead" state, then we dont even need to specify 
        // starting state, as it must implicitly be that it is "alive"

        // anyways, iterate over each line in the file
        // with each line just update state of cells in simulation's grid
        try 
        {
            File myfile = new File("res/test.txt");
            Scanner simulationDataFile = new Scanner(myfile);

            // first line is meant for the dimensions of the grid (w, h)
            int simulationWidth = -1;
            int simulationHeight = -1;

            ArrayList<Integer> cellData = new ArrayList<Integer>();

            for (int lineIndex = 0; simulationDataFile.hasNextLine(); ++lineIndex) 
            {
                // prase line into array of ints
                final String line = simulationDataFile.nextLine();

                final String TOKEN_DELIMITER = " ";
                
                final String[] stringTokens = line.split(TOKEN_DELIMITER);
                final int[] tokens = new int[stringTokens.length];

                for (int i = 0; i < stringTokens.length; ++i)
                    tokens[i] = Integer.parseInt(stringTokens[i]);
                
                if (lineIndex == 0)
                {    
                    simulationWidth = tokens[0];
                    simulationHeight = tokens[1];
                }
                else    // these are coordinates for living cells
                {
                    final int x = tokens[0];
                    final int y = tokens[1];

                    cellData.add(x);
                    cellData.add(y);
                }
        }
            simulationDataFile.close();

            simulation = new Simulation(simulationWidth, simulationHeight, cellData);

        }
        catch (FileNotFoundException e) 
        {
            System.out.println("Error.");
            e.printStackTrace();
        }

        
    }

    private void gameLoop()
    {
        while (isRunning)
        {
            simulation.tick();

            renderer.render(simulation);
        
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e) {}
        }
    }
}