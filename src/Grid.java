// just a grid of cells
public class Grid 
{
    // print message when attempting to access out of bound element
    private static final boolean DEBUG_MODE = false;  
    
    private static final Cell defaultCell = new Cell();

    private final int width;
    private final int height;
    private final int size;

    private Cell[] cells;

    public Grid(final int width, final int height)
    {
        this.width = width;
        this.height = height;
        this.size = width * height;

        this.cells = new Cell[size];
        
        for (int i = 0; i < size; ++i)
        {
            cells[i] = new Cell();
        }
    }

    Cell getCell(final int index)
    {
        final boolean isOutOfBounds = index < 0 || index >= size;

        if (isOutOfBounds)
        {
            if (DEBUG_MODE)
                System.out.println("DEBUG: attempting to access out of bounds element");
            
            return defaultCell;
        }
        else
            return cells[index];

    } 

    Cell getCell(final int x, final int y)
    {
        final int index = x + (y * width);
        return getCell(index);
    }

    final int getWidth()    { return width;  }
    final int getHeight()   { return height; }
    final int getSize()     { return size;   }
}
