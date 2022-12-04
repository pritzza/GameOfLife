public class Cell 
{    
    private CellState state;

    public Cell()
    {
        state = CellState.ERROR;
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

        final char INVALID_SYMBOL = 'E';
        return INVALID_SYMBOL;
    }    
}
