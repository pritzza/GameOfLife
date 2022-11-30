public class Cell 
{
    public enum CellState
    {
        Alive,
        Dead
    };

    private CellState state;

    public Cell()
    {
        state = CellState.Dead;
    }

    public void setState(final CellState s)
    {
        state = s;
    }

    public CellState getState()
    {
        return state;
    }

    public final char getSymbol()
    {
        switch (state)
        {
            case Alive:     return 'a';
            case Dead:      return '.';
        }

        final char INVALID_SYMBOL = 'I';
        return INVALID_SYMBOL;
    }    
}
