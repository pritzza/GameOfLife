import java.lang.Thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import java.util.ArrayList;

public class GameOfLife
{
    // ms per simulation step output
    private int simulationDisplaySpeed = 1000;
    private int simulationStepBeginning = 0;    // tracks at which step of the simulation should start being displayed
    private int simulationStepEnd = 0;          // tracks at which step of the simulation should end

    private final String SIMULATION_SAVES_DIR = "simulation saves/";

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
        programLoop();
    }

    private void programLoop()
    {
        while (isRunning)
        {
            final int input = takeMenuInput();
            switch (input)
            {
                case 1: createSimulation(); break;
                case 2: playSimulation(); break;
                case 3: isRunning = false; break;
            }
        }
    }

    final private int takeMenuInput()
    {
        System.out.println(
                            "Enter what you want to do:\n"
                            + "1) Create Simulation\n"
                            + "2) Play Simulation\n"
                            + "3) Quit\n"
                        );

        Scanner scanner = new Scanner(System.in);

        int input = -1;
        
        // while input is invalid
        while (input < 1 || input > 3)
            input = scanner.nextInt();

        return input;
    }

    private void createSimulation()
    {
        System.out.println("Enter simulation name");

        Scanner scanner = new Scanner(System.in);
        final String simulationFileName = SIMULATION_SAVES_DIR + scanner.nextLine();

        try
        {
            File simulationFile = new File(simulationFileName);
            
            final boolean fileCreated = simulationFile.createNewFile();
            
            if (fileCreated == false)
            {
                System.out.println("Error: Couldn't create file");
                return;
            }

            FileWriter simulationWriter = new FileWriter(simulationFile);

            System.out.println(
                                "Enter dimensions of simulation "
                              + "(width then height seperated by a space)"
                              );
            
            final int width  = scanner.nextInt();
            final int height = scanner.nextInt();
            scanner.nextLine();  // flush input buffer

            final String simulationDimensionString = width + " " + height + '\n';
            simulationWriter.write(simulationDimensionString);

            System.out.println(
                                "Enter coordinates of starting cells "
                              + "(x then y seperated by a space, "
                              + "enter \"done\" when finished)"
                              );
            
            String input = scanner.nextLine();

            // if should quit
            while (input.equals("done") == false)
            {
                // tokenize and validate user input for coordinates
                final String TOKEN_DELIMITER = " ";
                String[] inputTokens = input.split(TOKEN_DELIMITER);

                final int x = Integer.valueOf(inputTokens[0]);
                final boolean isValidX = (x >= 0 && x < width);

                final int y = Integer.valueOf(inputTokens[1]);
                final boolean isValidY = (y >= 0 && y < height);

                if (isValidX && isValidY)
                    simulationWriter.write(x + " " + y + '\n');
                else
                    System.out.println("Error: Invalid coordinates entered");

                input = scanner.nextLine();
            }

            simulationWriter.close();

        }
        catch (Exception e)
        {
            System.out.println("Error: something happened :(");
        }
    }

    private void playSimulation()
    {
        // enter a simulation to load (enter file name)
        System.out.println("Enter simulation name: ");
        
        final Scanner scanner = new Scanner(System.in);
        final String simulationFileName = scanner.nextLine();
        
        this.simulation = loadSimulation(simulationFileName);

        if (this.simulation == null)
            System.out.println("Error: Couldn't load simulation");

        // enter number of steps you want to see
        System.out.println("Enter at which generation to start showing simuatlion");
        int simStart = -1;

        // while input is invalid
        while (simStart < 0)
            simStart = scanner.nextInt();
        
        simulationStepBeginning = simStart;

        System.out.println("Enter at which generation to end simulation");
        int simStop = -1;
        
        // while input is invalid
        while (simStop < 0)
            simStop = scanner.nextInt();
            
        simulationStepEnd = simStop;
            
        System.out.println("Enter simulation display speed (ms per generation)");
        int simSpeed = -1;
        
        // while input is invalid
        while (simSpeed < 0)
            simSpeed = scanner.nextInt();
            
        simulationDisplaySpeed = simSpeed;

        simulationLoop();
    }

    private Simulation loadSimulation(final String simulationFileName)
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
            File myfile = new File(SIMULATION_SAVES_DIR + simulationFileName);
            Scanner simulationDataFile = new Scanner(myfile);

            int simulationWidth = -1;
            int simulationHeight = -1;

            ArrayList<Integer> cellData = new ArrayList<Integer>();

            for (int lineIndex = 0; simulationDataFile.hasNextLine(); ++lineIndex) 
            {
                // parse line into array of ints
                final String line = simulationDataFile.nextLine();

                final String TOKEN_DELIMITER = " ";
                
                final String[] stringTokens = line.split(TOKEN_DELIMITER);
                final int[] tokens = new int[stringTokens.length];
                
                for (int i = 0; i < stringTokens.length; ++i)
                    tokens[i] = Integer.parseInt(stringTokens[i]);
                
                // first line is meant for the dimensions of the grid (w, h)
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

            return new Simulation(simulationWidth, simulationHeight, cellData);

        }
        catch (FileNotFoundException e) 
        {
            System.out.println("Error: Couldn't find file.");
            e.printStackTrace();
        }        

        return null;
    }

    private void simulationLoop()
    {
        if (simulation == null)
            System.out.println("Error: couldn't play simulation"); 
        else
        {
            for (int i = 0; i < simulationStepEnd; ++i)
            {
                simulation.tick();

                final boolean shouldDisplay = i >= simulationStepBeginning;

                if (shouldDisplay)
                {
                    renderer.render(simulation);
        
                    try
                    {
                        Thread.sleep(simulationDisplaySpeed);
                    }
                    catch (Exception e) {}
                }
            }
        }
    }
}