// just a grid of cells
public class Grid 
{
    // print message when attempting to access out of bound element
    private static final boolean DEBUG_MODE = false;  
    
    private static final Cell defaultCell = new Cell();

    private final int width;
    private final int height;

    private final Cell[] cells;

    public Grid(final int width, final int height)
    {
        this.width = width;
        this.height = height;
        final int size = width * height;

        this.cells = new Cell[size];
        
        for (int i = 0; i < size; ++i)
        {
            cells[i] = new Cell();
        }
    }

    Cell getCell(final int index)
    {
        final boolean isInBounds = (index >= 0 && index < cells.length);

        if (isInBounds)
            return cells[index];
        else
        {
            if (DEBUG_MODE)
                System.out.println("DEBUG: attempting to access out of bounds element");
            
            return defaultCell;
        }

    } 

    Cell getCell(final int x, final int y)
    {
        final boolean isInBounds = (x >= 0 && x < width);

        if (isInBounds)
        {
            final int index = x + (y * width);
            return getCell(index);
        }
        else    // if y is out of bounds, getCell(int) will catch that
        {
            if (DEBUG_MODE)
                System.out.println("DEBUG: attempting to access out of bounds element");
            
            return defaultCell;
        }

    }

    final int getWidth()    { return width;        }
    final int getHeight()   { return height;       }
    final int getSize()     { return cells.length; }
}
