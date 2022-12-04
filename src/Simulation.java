import java.util.ArrayList;

//import CellState;

//import static CellState;

public class Simulation 
{
    private final static boolean PRINT_DEBUG_DATA = false;

    private Grid currentGrid;
    private Grid nextGrid;

    public Simulation(final int width, final int height, final ArrayList<Integer> initialState)
    {
        currentGrid = new Grid(width, height);
        nextGrid = new Grid(width, height);

        setInitialState(nextGrid, initialState);
    }

    private void reset(Grid grid)
    {
        final CellState DEFAULT_STARTING_CELL_STATE = CellState.Dead;

        for (int i = 0; i < grid.getSize(); ++i)
        {
            Cell c = grid.getCell(i);
            c.setState(DEFAULT_STARTING_CELL_STATE);
        }
    }

    private void setInitialState(final Grid grid, final ArrayList<Integer> data)
    {
        // set all cells to default state
        reset(grid);

        // go through explicitly defined initial state data
        // in current version, sets cells at given coordinates to be alive
        for (int i = 0; i < data.size(); i += 2)
        {
            final int x = data.get(i);
            final int y = data.get(i+1);

            final Cell c = grid.getCell(x, y);
            c.setState(CellState.Alive);;
        }
    }

    // progresses simulation to next state
    public void tick()
    {
        swapGrids();

        // go through every cell and apply the game of life's rules
        for (int x = 0; x < currentGrid.getWidth(); ++x)
            for (int y = 0; y < currentGrid.getHeight(); ++y)
            {
                final int neighbors = countNeighbors(x, y);

                if (PRINT_DEBUG_DATA)
                    if (neighbors > 0)
                        System.out.println("(" + x + ", " + y + "): has " + neighbors + " neighbors");

                final Cell cell = currentGrid.getCell(x, y);
                final CellState currentState = cell.getState();

                CellState nextState = CellState.ERROR;

                switch (currentState)
                {
                    case Alive:
                        if (neighbors == 2 || neighbors == 3)
                            nextState = CellState.Alive;
                        else
                            nextState = CellState.Dead;
                    break;

                    case Dead:
                        if (neighbors == 3)
                            nextState = CellState.Alive;
                        else
                            nextState = CellState.Dead;
                    break;

                    default:
                        System.out.println("ERROR: Unknown cell state");
                }

                // set state of next generation's cell
                final Cell nextCell = nextGrid.getCell(x, y);
                nextCell.setState(nextState);
            }
    }
    
    private void swapGrids()
    {
        final Grid temp = currentGrid;
        currentGrid = nextGrid;
        nextGrid = temp;
    }

    // returns number of living adjacent cells
    // (counts out of bounds cells as dead)
    final private int countNeighbors(final int x0, final int y0)
    {
        int neighborCount = 0;

        for (int x = -1; x < 2; ++x)
            for (int y = -1; y < 2; ++y)
            {
                // don't include cell for which we are counting the neighbors of
                if (x != 0 || y != 0)
                {
                    final int nX = x0 + x;  // neighbor x
                    final int nY = y0 + y;  // neighbor y

                    final Cell neighbor = currentGrid.getCell(nX, nY);
                    final boolean isNeighborAlive = neighbor.getState() == CellState.Alive;
                                    
                    if (isNeighborAlive)
                        neighborCount++;

                    if (PRINT_DEBUG_DATA)
                        System.out.println("(" + nX + ", " + nY + "): " + neighbor.getState());
                }
            }
        
        return neighborCount;
    }

    final Grid getGrid()
    {
        return currentGrid;
    }

}
