import Cell.CellState;

//import static Cell.CellState;

public class Simulation 
{
    private final Grid grid;

    public Simulation(final int width, final int height)
    {
        grid = new Grid(width, height);

        reset();
    }

    public void reset()
    {
        // todo: figure out how to import enum so "Cell." can be dropped
        final Cell.CellState DEFAULT_CELL_STATE = Cell.CellState.Dead;

        for (int i = 0; i < grid.getSize(); ++i)
        {
            Cell c = grid.getCell(i);
            c.setState(DEFAULT_CELL_STATE);

            if (i == 12)
                c.setState(Cell.CellState.Alive);
        }

    }

    // progresses simulation to next state
    public void tick()
    {

    }
    
    final Grid getGrid()
    {
        return grid;
    }

}
