public class MatrixSolver {

  public static boolean Solvable(Matrix M){
    int i = 1;
    boolean retSol = true;
    while (retSol && i<=M.bar) {
      if (M.IsRowConstZero(i) && !M.IsResZero(i)) {
        retSol = false;
      }
    }
    return retSol;
  }

  public static String[] Solve(Matrix M){
    String[] retArray = new String[M.kol];
    for (int i=1; i<=M.kol; i++) {
      retArray[i] = Character.toString((char) (i + 97));
    }
    return retArray;
  }
}
