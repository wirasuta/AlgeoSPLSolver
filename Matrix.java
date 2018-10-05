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
  public void InputMatrixSPL (int src) throws FileNotFoundException{
    Scanner inp = new Scanner(System.in);
    if (src == 1){
      for ( int i=1; i<=this.bar; i++) {
        for ( int j=1; j<=this.kol; j++) {
          this.TabInt[i][j] = inp.nextDouble();
        }
      }
    }else if (src == 2){
      System.out.print("Masukkan lokasi file augmented matrix: ");
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
      this.bar = rows;
      this.kol = columns;
      this.TabInt = new double[rows+1][columns+1];
      input.close();

      //Membaca elemen matrix
      input = new Scanner(new File(fileName));
      for(int i = 1; i <= this.bar; i++)
      {
         for(int j = 1; j <= this.kol; j++)
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
  public void InputMatrixInterpolasi (int src) throws FileNotFoundException{
    if (src == 1){
      Matrix inpMatrix = new Matrix(this.bar,2);
      inpMatrix.InputMatrixSPL(1);
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
    }else if (src == 2){
      Scanner in = new Scanner (System.in);
      System.out.print("Masukkan lokasi file titik yang akan diinterpolasi: ");
      String fileName = in.nextLine();
      //Scanner untuk file external
      Scanner input = new Scanner (new File(fileName));

      //Membaca jumlah baris dan kolom
      int rows = 0;
      int columns = 2;
      while(input.hasNextLine())
      {
        ++rows;
        input.nextLine();
      }
      //Mengubah jumlah kolom dan baris
      this.bar = rows;
      this.kol = rows+1;
      this.TabInt = new double[this.bar+1][this.kol+1];
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
        System.out.printf("%f ",this.TabInt[i][j]);
      }
      System.out.print("\n");
    }
  }

  //Operasi pada matrix augmented
  //Informasi mengenai suatu baris

  //Memeriksa apakah satu baris koefisien terdiri seluruhnya dari nol
  public boolean IsRowCoefZero(int i){
    int j = 1;
    boolean allZero = true;
    while (j<this.kol && allZero) {
      if (this.TabInt[i][j] != 0) {
        allZero = false;
      }else{
        j++;
      }
    }
    return allZero;
  }

  //Memeriksa apakah elemen hasil baris ke-i adalah nol
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

  public int PosLeadingElmt(int i){
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
    //Mengembalikan posisi leading 1
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
        if (this.TabInt[i][this.PosLeadingElmt(i)] != 0) {
          ratio = this.TabInt[j][this.PosLeadingElmt(i)]/this.TabInt[i][this.PosLeadingElmt(i)];
          for ( int z = 1; z <= this.kol; z++) {
            this.TabInt[j][z] -= ratio * this.TabInt[i][z];
          }
        }
      }
    }

    //Normalisasi bilangan mendekati 0 menjadi 0
    for ( int i = 1; i <= this.bar; i++) {
      for ( int j = 1; j <= this.kol; j++) {
        if (Elmt(i,j)<1E-20 && Elmt(i,j)>-1E-20) {
          this.TabInt[i][j] = 0;
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

    for ( int i = 1; i <= this.bar; i++) {
      for ( int j = i+1; j <= this.bar; j++) {
        if (this.TabInt[i][this.PosLeadingElmt(i)] != 0) {
          ratio = this.TabInt[j][this.PosLeadingElmt(i)]/this.TabInt[i][this.PosLeadingElmt(i)];
          for ( int z = 1; z <= this.kol; z++) {
            this.TabInt[j][z] -= ratio * this.TabInt[i][z];
          }
        }
      }
    }

    //Step 3 : Mengalikan tiap baris dengan konstanta ratio sehingga memiliki leading 1
    for ( int i = 1; i <= this.bar; i++) {
      if (!this.IsRowCoefZero(i)) {
        int posLeading = this.PosLeadingElmt(i);
        double ratioLeadingOne = 1/Elmt(i,posLeading);
        for (int j = posLeading; j<=this.kol; j++) {
          this.TabInt[i][j] *= ratioLeadingOne;
        }
      }
    }
  }

  public void REF2RREF(){
    //Prekondisi : Matrix REF
    double ratio;
    boolean leading1;

    //Mengurangi tiap elemen di atas leading one dengan OBE sehingga terbentuk RREF
    //Old OBE algorithm
    //for ( int i = 1; i < this.bar; i++) {
    //  for (int j = i+1; j <= this.bar; j++) {
    //    ratio = 1;
    //    leading1 = true;
    //    for ( int k = 1; k <= this.kol; k++) {
    //      if (leading1 && Elmt(j,k) != 0){
    //        leading1 = false;
    //        ratio = Elmt(i,k)/Elmt(j,k);
    //        this.TabInt[i][k] -= ratio*Elmt(j,k);
    //      }else if (!leading1){
    //        this.TabInt[i][k] -= ratio*Elmt(j,k);
    //      }
    //    }
    //  }
    //}

    //Normalisasi bilangan mendekati 0 menjadi 0
    for ( int i = 1; i <= this.bar; i++) {
      for ( int j = 1; j <= this.kol; j++) {
        if (Elmt(i,j)<1E-20 && Elmt(i,j)>-1E-20) {
          this.TabInt[i][j] = 0;
        }
      }
    }

    for ( int i = this.bar; i > 1; i--) {
      for (int j = i-1; j >= 1; j--) {
        if (!IsRowCoefZero(i)) {
          int leadPos = PosLeadingElmt(i);
          ratio = Elmt(j,leadPos)/Elmt(i,leadPos);
          for (int k = leadPos; k<=this.kol; k++) {
            this.TabInt[j][k] -= ratio*Elmt(i,k);
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
  }
}
