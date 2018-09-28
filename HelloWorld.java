import java.util.Scanner;

public class HelloWorld{
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
  }
}
