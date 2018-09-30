public class Solver {

  public static boolean MatrixSolvable(Matrix M){
    int i = 1;
    boolean retSol = true;
    while (retSol && i<=M.bar) {
      if (M.IsRowCoefZero(i) && !M.IsResZero(i)) {
        retSol = false;
      }else{
        i++;
      }
    }
    return retSol;
  }

  public static String[] MatrixSolve(Matrix M){
    //Prekondisi : Matrix Solvable
    String[] retArray = new String[M.kol+1];
    for (int i=1; i<=M.kol; i++) {
      retArray[i] = Character.toString((char) (i + 97));
    }
    for (int i=M.bar; i>=1; i--) {
      if (!M.IsRowCoefZero(i)) {
        if (M.OnlyLeadingOne(i)) {
          //Hanya ada leading one pada baris ke-i
          retArray[M.PosLeadingOne(i)] = Float.toString(M.Elmt(i,M.kol));
        }else{
          //Ada elemen non-0 setelah leading one pada baris ke-i
          //TODO
        }
      }
    }
    return retArray;
  }
}
