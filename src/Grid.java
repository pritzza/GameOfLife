// just a grid of cells
public class Grid 
{
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
            cells[i] = new Cell();
    }

    Cell getCell(final int index)
    {
        return cells[index];
    } 

    Cell getCell(final int x, final int y)
    {
        final int index = (x % width) + (y / width);
        return getCell(index);
    }

    final int getWidth()    { return width;  }
    final int getHeight()   { return height; }
    final int getSize()     { return size;   }
}
