public class GameOfLife
{
    private Simulation simulation;
    private Renderer renderer;

    private static boolean isRunning = false;

    public GameOfLife(final int simulationWidth, final int simulationHeight)
    {
        simulation = new Simulation(simulationWidth, simulationHeight);
        renderer = new Renderer();
    }

    public void start()
    {
        // load starting state from file
        // idea: prompt user to enter filename

        isRunning = true;
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
    }

    private void gameLoop()
    {
        //while (isRunning)
        {
            simulation.tick();

            renderer.render(simulation);
        }
    }
}