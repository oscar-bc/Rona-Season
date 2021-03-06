import Classes.Solution;

import java.io.IOException;
import java.util.List;

public interface Solver {
    public List<Solution> solve(int fileSize, boolean custom, int GPAInput) throws IOException;
    public List<Solution> solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations, int fileSize, boolean custom, int GPAInput) throws IOException;
}

