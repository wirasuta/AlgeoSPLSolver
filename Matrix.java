import java.util.Scanner;

public class Matrix {

  private float[][] TabInt;
  private int kol;
  private int bar;

  Matrix(int x, int y){
    this.TabInt = new float[x+1][y+1];
    this.bar = x;
    this.kol = y;
    InputMatrix();
  }

  public float Elmt(int i, int j){
    return this.TabInt[i][j];
  }

  public void PrintMatrix(){
    for ( int i=1; i<=this.bar; i++) {
      for ( int j=1; j<=this.kol; j++) {
        System.out.print(this.TabInt[i][j]+" ");
      }
      System.out.print("\n");
    }
  }

  public void Matrix2REF(){
    /* Prekondisi : Matrix Augmented */
    /* Row reduction : 1. Urutkan berdasarkan leading 0
                       2. Kerjakan dari row paling pertama dengan membuat leading 1
                       3. Buat 0 di row di bawah leading 1 atas berikutnya, lalu kalikan dengan konstanta row tersebut untuk membuat leading 1*/
    //Step 1 : Operasi Baris Elementer
    float ratio;
    for ( int i = 1; i <= this.bar; i++) {
      for ( int j = i+1; j <= this.bar; j++) {
        if (this.TabInt[i][i] != 0) {
          ratio = this.TabInt[j][i]/this.TabInt[i][i];
          for ( int z = 1; z <= this.kol; z++) {
            this.TabInt[j][z] -= ratio * this.TabInt[i][z];
          }
        }
      }
    }

    //Step 2 : Menukar baris untuk mengurutkan 0
    for ( int i = 1; i < this.bar; i++) {
      //Mencari jumlah nol di baris ke-i
      int zeroi,j;
      zeroi = 0; j = 1;
      while (j < this.kol && Elmt(i,j) == 0) {
        zeroi++;
        j++;
      }
      //Mencari jumlah nol di baris setelah baris ke-i
      int rowSw,zeroRowSw;
      rowSw = -1; zeroRowSw = zeroi;
      for ( int k = i+1; k <= this.bar; k++) {
        int zerok,l;
        zerok = 0; l = 1;
        while (l < this.kol && Elmt(k,l) == 0) {
          zerok++;
          l++;
        }
        if (zerok<zeroRowSw){
          rowSw = k;
          zeroRowSw = zerok;
        }
      }
      //Menukar 2 baris
      if (rowSw != -1) {
        float temp;
        for ( int m = 1; m <= this.kol; m++) {
          temp = Elmt(i,m);
          this.TabInt[i][m] = Elmt(rowSw,m);
          this.TabInt[rowSw][m] = temp;
        }
      }
    }

    boolean leading1;
    //Step 3 : Mengalikan tiap baris sehingga memiliki leading 1
    for ( int i = 1; i <= this.bar; i++) {
      ratio = 1;
      leading1 = true;
      for ( int j = 1; j <= this.kol; j++) {
        if (leading1 && Elmt(i,j) != 0){
          leading1 = false;
          ratio = 1/Elmt(i,j);
          this.TabInt[i][j] *= ratio;
        }else if (!leading1 && Elmt(i,j) != 0){
          this.TabInt[i][j] *= ratio;
        }
      }
    }
  }

  public void REF2RREF(){
    float ratio;
    boolean leading1;

    for ( int i = 1; i < this.bar; i++) {
      for (int j = i+1; j <= this.bar; j++) {
        ratio = 1;
        leading1 = true;
        for ( int k = 1; k <= this.kol; k++) {
          if (leading1 && Elmt(j,k) != 0){
            leading1 = false;
            ratio = Elmt(i,k)/Elmt(j,k);
            this.TabInt[i][k] -= ratio*Elmt(j,k);
          }else if (!leading1){
            this.TabInt[i][k] -= ratio*Elmt(j,k);
          }
        }
      }
    }
  }

  private void InputMatrix(){
    Scanner inp = new Scanner(System.in);
    for ( int i=1; i<=this.bar; i++) {
      for ( int j=1; j<=this.kol; j++) {
        this.TabInt[i][j] = inp.nextFloat();
      }
    }
  }
}
