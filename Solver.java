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
    String[] retArray = new String[M.kol];
    for (int i=1; i<M.kol; i++) {
      retArray[i] = Character.toString((char) (i + 96));
    }
    for (int i=M.bar; i>=1; i--) {
      if (!M.IsRowCoefZero(i)) {
        if (M.OnlyLeadingOne(i)) {
          //Hanya ada leading one pada baris ke-i
          retArray[M.PosLeadingOne(i)] = Float.toString(M.Elmt(i,M.kol));
        }else{
          //Ada elemen non-0 setelah leading one pada baris ke-i
          float resFloat = M.Elmt(i,M.kol);
          String resString = "";
          for (int j=i+1; j<M.kol; j++) {
            if (M.Elmt(i,j) != 0) {
              //Kondisi elemen M ke i,j bukan 0
              try {
                //Jika hasil ke-x merupakan bilangan, maka jumlahkan dengan elemen hasil
                resFloat += Float.valueOf(retArray[j]);
              } catch(NumberFormatException e) {
                //Jika hasil ke-x bukan bilangan, sambungkan koefisien dengan parameter yang sesuai
                resString += ConCoefParam((-1)*M.Elmt(i,j),retArray[j]);
              }
            }
          }
          //Gabungkan bilangan hasil dengan parameter
          retArray[M.PosLeadingOne(i)] = Float.toString(resFloat) + resString;
        }
      }
    }
    return retArray;
  }

  private static String ConCoefParam(float coef, String param){
    if (coef>1) {
      return "+" + Float.toString(coef) + param;
    }else if (coef == 1){
      return "+" + param;
    }else if (coef == -1){
      return "-" + param;
    }else{
      return Float.toString(coef) + param;
    }
  }

  public static float SolveInterpolasi(String[] S,float x) {
	 float y = 0;
   float s;
	 // penyelesaian f(x) setelah fungsi interpolasi terbentuk
   for (int i=1; i<S.length; i++) {
     s = Float.valueOf(S[i]);
     y += (s * Math.pow(x,i-1));
   }
   return y;
 }
}
