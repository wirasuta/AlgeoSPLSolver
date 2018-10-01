import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Matrix {

  public double[][] TabInt;
  public int kol;
  public int bar;

  //Konstruktor Matrix
  //Mengabaikan baris dan kolom index 0
  public Matrix(int x, int y){
    this.TabInt = new double[x+1][y+1];
    this.bar = x;
    this.kol = y;
  }

  //Getter Elemen
  public double Elmt(int i, int j){
    return this.TabInt[i][j];
  }

  //Input matrix SPL dari input user
  //F.S. TabInt pada objek terdefinisi
  public void InputMatrixSPL (char src) throws FileNotFoundException{
    Scanner inp = new Scanner(System.in);
    if (src == 'I'){
      for ( int i=1; i<=this.bar; i++) {
        for ( int j=1; j<=this.kol; j++) {
          this.TabInt[i][j] = inp.nextDouble();
        }
      }
    }else if (src == 'E'){
      System.out.print("Masukkan lokasi file augmented matrix: ");
      inp.nextLine();
      String fileName = inp.nextLine();
      //Scanner untuk file external
      Scanner input = new Scanner (new File(fileName));

      //Membaca jumlah baris dan kolom
      int rows = 0;
      int columns = 0;
      while(input.hasNextLine())
      {
         ++rows;
         columns = 0;
         Scanner colReader = new Scanner(input.nextLine());
         while(colReader.hasNextDouble())
         {
             ++columns;
             colReader.nextDouble();
         }
      }

      //Mengubah jumlah kolom dan baris
      this.kol = columns;
      this.bar = rows;
      input.close();

      //Membaca elemen matrix
      input = new Scanner(new File(fileName));
      for(int i = 0; i < this.bar; i++)
      {
         for(int j = 0; j < this.kol; j++)
         {
             if(input.hasNextDouble())
             {
                 this.TabInt[i][j] = input.nextDouble();
             }
         }
      }
    }
  }

  //Input matrix interpolasi, kemudian mengubahnya menjadi matrix SPL
  //F.S. Matrix SPL hasil dari titik-titik pada derajat n terdefinisi
  public void InputMatrixInterpolasi (char src) throws FileNotFoundException{
    if (src == 'I'){
      Matrix inpMatrix = new Matrix(this.bar,2);
      inpMatrix.InputMatrixSPL('I');
      //Mengubah matrix titik ke matrix SPL
      for ( int i=1; i<=this.bar; i++) {
        for ( int j=1; j<=this.kol; j++) {
          if (j == 1) {
            this.TabInt[i][j] = 1;
          }
          else { // nilai elemen untuk kolom selain 1
            if (j != this.kol) {
              this.TabInt[i][j] = Math.pow(inpMatrix.TabInt [i][1], j-1);
            }
            else { // jika di akhir kolom masukan nilai augmentednya
              this.TabInt[i][j] = inpMatrix.TabInt [i][2];
            }
          }
        }
      }
    }else if (src == 'E'){
      Scanner in = new Scanner (System.in);
      System.out.print("Masukkan lokasi file titik yang akan diinterpolasi: ");
      in.nextLine();
      String fileName = in.nextLine();
      //Scanner untuk file external
      Scanner input = new Scanner (new File(fileName));

      //Membaca jumlah baris dan kolom
      int rows = 0;
      int columns = 2;
      while(input.hasNextLine())
      {
          ++rows;
      }
      //Mengubah jumlah kolom dan baris
      this.bar = rows;
      this.kol = rows+1;
      input.close();

      //Input matrix titik
      Matrix inpMatrix = new Matrix(this.bar,2);
      //Membaca titik ke dalam matrix
      input = new Scanner(new File(fileName));
      for(int i = 1; i <= this.bar; i++){
        for(int j = 1; j <= 2; j++)
        {
            if(input.hasNextDouble())
            {
                inpMatrix.TabInt[i][j] = input.nextDouble();
            }
        }
      }

      //Mengubah matrix titik ke matrix SPL
      for ( int i=1; i<=this.bar; i++) {
        for ( int j=1; j<=this.kol; j++) {
          if (j == 1) {
            this.TabInt[i][j] = 1;
          }
          else { // nilai elemen untuk kolom selain 1
            if (j != this.kol) {
              this.TabInt[i][j] = Math.pow(inpMatrix.TabInt [i][1], j-1);
            }
            else { // jika di akhir kolom masukan nilai augmentednya
              this.TabInt[i][j] = inpMatrix.TabInt [i][2];
            }
          }
        }
      }
    }
  }

  //Mencetak matrix
  public void PrintMatrix(){
    for ( int i=1; i<=this.bar; i++) {
      for ( int j=1; j<=this.kol; j++) {
        System.out.print(this.TabInt[i][j]+" ");
      }
      System.out.print("\n");
    }
  }

  //Operasi pada matrix augmented
  //Informasi mengenai suatu baris
  public boolean IsRowCoefZero(int i){
    int j = 1;
    boolean allZero = true;
    while (j<=this.kol && allZero) {
      if (this.TabInt[i][j] != 0) {
        allZero = false;
      }else{
        j++;
      }
    }
    return allZero;
  }

  public boolean IsResZero(int i){
    return (this.TabInt[i][this.kol] == 0);
  }

  public boolean OnlyLeadingOne(int i){
    boolean retLeadingOne, leadingOneFound;
    retLeadingOne = true;
    leadingOneFound = false;
    for ( int j = 1; j < this.kol; j++) {
      //Mencari leading one
      if (!leadingOneFound && Elmt(i,j) != 0){
        leadingOneFound = true;
      } else if (leadingOneFound && Elmt(i,j) != 0) {
        //Memeriksa elemen setelah leading one hingga sebelum kolom hasil
        retLeadingOne = false;
      }
    }
    return retLeadingOne;
  }

  public int PosLeadingOne(int i){
    //Prekondisi : Baris ke-i memiliki leading 1
    boolean leadingOneFound;
    int j;
    j = 1;
    leadingOneFound = false;
    while (!leadingOneFound && j<this.kol) {
      //Mencari leading one
      if (!leadingOneFound && Elmt(i,j) != 0){
        leadingOneFound = true;
      }else{
        j++;
      }
    }
    return j;
  }

  //Operasi Mengubah Matrix Augmented ke REF/RREF
  public void Matrix2REF(){
    //Prekondisi : Matrix Augmented
    /* Row reduction : 1. Lakukan OBE untuk membuat elemen di bawah lead elemen pada kolom yang sama menjadi 0
                       2. Urutkan berdasarkan jumlah 0 di depan leading elemnt
                       3. Mengalikan satu baris dengan konstanta ratio untuk membuat leading 1*/
    //Step 1 : Operasi Baris Elementer
    double ratio;
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
        double temp;
        for ( int m = 1; m <= this.kol; m++) {
          temp = Elmt(i,m);
          this.TabInt[i][m] = Elmt(rowSw,m);
          this.TabInt[rowSw][m] = temp;
        }
      }
    }

    boolean leading1;
    //Step 3 : Mengalikan tiap baris dengan konstanta ratio sehingga memiliki leading 1
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
    //Prekondisi : Matrix REF
    double ratio;
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
}
