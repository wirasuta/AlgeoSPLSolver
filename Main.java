import java.util.Scanner;

public class Main{
  public static void main(String[] args) {
    int m,n;

    System.out.print("Masukkan BARIS <spasi> KOLOM : ");

    Scanner inp = new Scanner(System.in);
    m = inp.nextInt();
    n = inp.nextInt();

    Matrix mat = new Matrix(m,n);
    mat.PrintMatrix();

    System.out.println("===========");

    mat.Matrix2REF();
    mat.PrintMatrix();

    System.out.println("===========");

    mat.REF2RREF();
    mat.PrintMatrix();

    System.out.println("===========");
    if (Solver.MatrixSolvable(mat)){
      String[] resArray = Solver.MatrixSolve(mat);
      for (int i=1;i<resArray.length;i++) {
          System.out.println("x"+i+": "+resArray[i]);
      }
    }else{
      System.out.println("Tidak ada solusi");
    }
  }
}
