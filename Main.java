import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main{
  public static void main(String[] args) {

    Scanner inp = new Scanner(System.in);

    //Pilihan program
    System.out.print("Penyelesaian [S]PL/[I]nterpolasi titik :");
    char choice = inp.next().charAt(0);

    //Inisialisasi matrix dan array jawaban kosong
    Matrix mat = new Matrix(0,0);
    String[] resArray = new String[0];

    if (choice == 'S') { //Penyelesaian SPL
      //int M,N;
      //System.out.print("Masukkan Baris dan Kolom (Augmented Matrix) : ");
      //M = inp.nextInt();
      //N = inp.nextInt();

      //System.out.println("Masukkan elemen matrix");
      //mat = new Matrix(M,N);
      try {
        mat.InputMatrixSPL('E');
      } catch(FileNotFoundException e) {
        System.out.print(e);
      }
      mat.Matrix2REF();
      mat.REF2RREF();

      mat.PrintMatrix();

      //Penyelesaian
      if (Solver.MatrixSolvable(mat)){
        resArray = Solver.MatrixSolve(mat);
        for (int i=1;i<resArray.length;i++) {
          System.out.println("x"+i+": "+resArray[i]);
        }
      }else{
        System.out.println("Tidak ada solusi");
      }
    } else if (choice == 'I'){ //Interpolasi dari Titik
      int N;
      //System.out.print("Masukkan Jumlah Titik : ");
      //N = inp.nextInt();

      //System.out.println("Masukkan elemen matrix");
      //mat = new Matrix(N,N+1);
      try {
        mat.InputMatrixInterpolasi('E');
      } catch(FileNotFoundException e) {
        System.out.print(e);
      }
      mat.Matrix2REF();
      mat.REF2RREF();

      //Asumsikan untuk permasalahan interpolasi solusi tepat satu
      resArray = Solver.MatrixSolve(mat);
      for (int i=1;i<resArray.length;i++) {
        System.out.println("a"+(i-1)+": "+resArray[i]);
      }

      System.out.print("Masukkan nilai x untuk diaproksimasi : ");
      float x = inp.nextFloat();
      System.out.println("Nilai y = "+Solver.SolveInterpolasi(resArray,x));
    }

    //Mencetak jawaban ke file external
    char c;
    //input user
    System.out.println("Apakah jawaban ingin disimpan ke file? (Y/N): ");
    c = inp.next().charAt(0);
    if ((c == 'Y') || (c == 'y')){
      Solver.SimpanJawabanKeFile(mat,resArray);
    }
  }
}
