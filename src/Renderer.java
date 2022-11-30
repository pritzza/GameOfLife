public class Renderer 
{
    public void render(final Simulation s)
    {
        render(s.getGrid());
    }    

    private void render(final Grid grid)
    {
        String outputBuffer = "";

        for (int i = 0; i < grid.getSize(); ++i)
        {
            final boolean needNewLine = (i % grid.getWidth() == 0 && i > 0);

            if (needNewLine)
                outputBuffer += '\n';

                
            final char cellSymbol = grid.getCell(i).getSymbol();

            outputBuffer += cellSymbol;
        }

        System.out.print(outputBuffer);
    }
}
