import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
            retArray[M.PosLeadingElmt(i)] = Double.toString(M.Elmt(i,M.kol));
         }else{
            //Ada elemen non-0 setelah leading one pada baris ke-i
            double resDouble = M.Elmt(i,M.kol);
            String resString = "";
            for (int j=M.PosLeadingElmt(i)+1; j<M.kol; j++) {
               if (M.Elmt(i,j) != 0) {
                  //Kondisi elemen M ke i,j bukan 0
                  try {
                     //Jika hasil ke-x merupakan bilangan, maka jumlahkan dengan elemen hasil
                     if (retArray[j].contains("d")) {
                        throw new NumberFormatException("contains d");
                     }
                     resDouble += (-1)*M.Elmt(i,j)*Double.valueOf(retArray[j]);
                  } catch(NumberFormatException e) {
                     //Jika hasil ke-x bukan bilangan, sambungkan koefisien dengan parameter yang sesuai
                     resString += ConCoefParam((-1)*M.Elmt(i,j),retArray[j]);
                  }
               }
            }
            //Gabungkan bilangan hasil dengan parameter
            if (resDouble != 0) {
               retArray[M.PosLeadingElmt(i)] = String.format("%.3f",resDouble) + resString;
            }else if (resDouble == 0 && resString == ""){
               retArray[M.PosLeadingElmt(i)] = String.format("%.3f",resDouble);
            }else{
               retArray[M.PosLeadingElmt(i)] = resString;
            }

            if (retArray[M.PosLeadingElmt(i)].startsWith("+")){
              retArray[M.PosLeadingElmt(i)] = retArray[M.PosLeadingElmt(i)].substring(1);
            }
         }
      }
   }
   return retArray;
}

private static String ConCoefParam(double coef, String param){
   if (param.length()>2 && coef!=1 && coef!=1) {
      param = "("+param+")";
   }
   if (coef>1) {
      return "+" + Double.toString(coef) + param;
   }else if (coef == 1) {
      return "+" + param;
   }else if (coef == -1) {
      return "-" + param;
   }else{
      return Double.toString(coef) + param;
   }
}

public static boolean IsNotInterpolasiable (Matrix m) {
   // Menghasilkan true jika persamaan interpolasi tak bisa diselesaikan dan false jika persoalan interpolasi dapat diselesaikan
   boolean isNotSolve = true;
   int j = 2;
   while ((isNotSolve) || (j<=m.kol)) {
      double temp = m.Elmt(1,j);
      int i = 2;
      while ((isNotSolve) && (i<=m.bar)) {
         if (temp != m.Elmt(i,j)) {
            isNotSolve = false;
         } else {
            i++;
         }
      }
      j++;
   }
   return isNotSolve;
}

public static double SolveInterpolasi(String[] S,double x) {
   double y = 0;
   double s;
   // penyelesaian f(x) setelah fungsi interpolasi terbentuk
   for (int i=1; i<S.length; i++) {
      s = Double.valueOf(S[i]);
      y += (s * Math.pow(x,i-1));
   }
   return y;
}

public static void SimpanJawabanKeFile(Matrix mat, String[] resArray){
   Scanner in = new Scanner(System.in);
   System.out.println("Masukan lokasi file penyimpanan : ");
   String fileName = in.nextLine();

   File file = new File(fileName);
   try {
      file.getParentFile().mkdirs();
   } catch(Exception e) {
   }

   try {
      PrintWriter printWriter = new PrintWriter(file);

      //Mencetak matrix REF/RREF sesuai dengan pilihan awal
      printWriter.printf("Matrix setelah proses: \n");
      for(int i = 1; i <= mat.bar; i++)
      {
         for(int j = 1; j < mat.kol; j++)
         {
            printWriter.printf("%.3f", mat.Elmt(i,j));
            printWriter.printf(" ");
         }
         printWriter.printf("%.3f",mat.Elmt(i,mat.kol));
         printWriter.printf("\n");
      }

      //Mencetak jawaban
      if (resArray[1] != null){
        printWriter.printf("Jawaban: \n");
        for (int k = 1; k < resArray.length; k++)
        {
          printWriter.printf("X%d = %s ", k, resArray[k]);
        }
      }else{
        printWriter.println("Tidak ada solusi");
      }

      printWriter.close();

   } catch (IOException e) {
      //Auto-generated catch block
      e.printStackTrace();
   }
}
}
