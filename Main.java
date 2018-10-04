import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {

private static Scanner in = new Scanner(System.in);

public static boolean isInputMenuValid (int inp) {
    return (inp == 1 || inp == 2 || inp == 3);
}


public static int inputMenu () {
    // KAMUS LOKAL
    int Choice;

    // ALGORITMA
    do {
        System.out.print("Pilih program dengan mengetik sesuai nomor urutan pada menu : ");
        Choice = in.nextInt();
        if (!isInputMenuValid(Choice)) {
            System.out.print("Input salah, hanya menerima input 1 s/d 3. Silahkan input kembali.\n");
        }
    } while (!isInputMenuValid(Choice)); // Input user sudah valid

    return Choice;
}

public static int inputMenuSolusi () {
    // KAMUS LOKAL
    int Choice;

    // ALGORITMA
    do {
        System.out.print("Pilih solusi dengan mengetik sesuai nomor urutan pada menu : ");
        Choice = in.nextInt();
        if (!isInputMenuValid(Choice)) {
            System.out.print("Input salah, hanya menerima input 1 s/d 3. Silahkan input kembali.\n");
        }
    } while (!isInputMenuValid(Choice)); // Input user sudah valid

    return Choice;
}

public static int pilihanInput () {
    // KAMUS LOKAL
    int Choice;

    // ALGORITMA
    do {
        System.out.print("Pilih inputan persoalan dengan mengetik sesuai nomor urutan pada menu : ");
        Choice = in.nextInt();
        if (!isInputMenuValid(Choice)) {
            System.out.print("Input salah, hanya menerima input 1 s/d 3\n. Silahkan input kembali.\n");
        }
    } while (!isInputMenuValid(Choice)); // Input user sudah valid

    return Choice;
}

public static void MenuSolusi () {
    System.out.print("PILIHAN SOLUSI\n");
    System.out.print("1. Metode Eliminasi Gauss\n");
    System.out.print("2. Metode Eliminasi Gauss-Jordan\n");
    System.out.print("3. Keluar\n");

}

public static void MenuInput () {
    System.out.print("PILIHAN INPUT\n");
    System.out.print("1. Keyboard\n");
    System.out.print("2. File Eksternal\n");
    System.out.print("3. Keluar\n");

}

public static void MainMenu () {
    System.out.print("MENU\n");
    System.out.print("1. Sistem Persamaan Linear\n");
    System.out.print("2. Interpolasi Polinom\n");
    System.out.print("3. Keluar\n");
}

public static void LastMenu () {
    System.out.print("\n");
    System.out.print("1. Ya \n");
    System.out.print("2. Tidak\n");
    System.out.print("3. Keluar\n");
}

public static void outputFileEksternal (Matrix mat,String [] resArray){
    //Mencetak jawaban ke file external
    char c;
    //input user
    System.out.println("Apakah jawaban ingin disimpan ke file? (Y/N): ");
    c = in.next().charAt(0);
    if ((c == 'Y') || (c == 'y')) {
        Solver.SimpanJawabanKeFile(mat,resArray);
    }
}

public static String[] SolusiSPL (Matrix m) {
    String[] rArray = new String[m.kol];
    if (Solver.MatrixSolvable(m)) {
        rArray = Solver.MatrixSolve(m);
        System.out.println("Solusi penyelesaian :\n");
        for (int i=1; i<rArray.length; i++) {
            System.out.println("x"+i+": "+rArray[i]);
        }
        System.out.println("\n");
    } else {
        System.out.println("Tidak ada solusi\n");
    }
    return rArray;
}

public static String[] SolusiInterpolasi (Matrix m) {
    //Asumsikan untuk permasalahan interpolasi solusi tepat satu
    String[] rArray = Solver.MatrixSolve(m);
    for (int i=1; i<rArray.length; i++) {
        System.out.println("a"+(i-1)+": "+rArray[i]);
    }

    System.out.print("Masukkan nilai x untuk diaproksimasi : ");
    float x = in.nextFloat();
    System.out.println("Nilai y = "+Solver.SolveInterpolasi(rArray,x));
    System.out.println("\n");
    return rArray;
}

public static int inputMenuAgain () {
    // KAMUS LOKAL
    int Choice;

    // ALGORITMA
    do {
        System.out.print("Apakah ingin menyelesaikan persoalan lagi (1..3)? ");
        Choice = in.nextInt();
        if (!isInputMenuValid(Choice)) {
            System.out.print("Input salah, hanya menerima input 1 s/d 3. Silahkan input kembali.\n");
        }
    } while (!isInputMenuValid(Choice)); // Input user sudah valid

    return Choice;
}
public static void main(String[] args) throws FileNotFoundException {

    boolean again = true;

    while (again) {

        //Inisialisasi matrix dan array jawaban kosong
        Matrix mat = new Matrix(0,0);
        String[] resArray= new String[0];
        boolean exit = false;

        while (!exit) {

            MainMenu ();
            int choice = inputMenu();

            if (choice == 1) {
                MenuSolusi();
                choice = inputMenuSolusi();
                if (choice  == 1) {
                    MenuInput();
                    choice = pilihanInput();
                    if (choice  == 1) {
                        //Input ukuran matrix dari user
                        System.out.print("Masukkan jumlah baris matrix augmented: ");
                        int nBar = in.nextInt();
                        System.out.print("Masukkan jumlah kolom matrix augmented: ");
                        int nKol = in.nextInt();
                        mat = new Matrix(nBar,nKol);
                        resArray = new String[nKol];

                        // Input permasalahan dari user
                        System.out.println("Masukkan matrix augmented:");
                        mat.InputMatrixSPL (choice);

                        // Pencarian solusi menggunakan metode Gauss
                        mat.Matrix2REF();

                        // Menampilkan matriks Row Echelon Form
                        System.out.print("MATRIKS ROW ECHELON FORM \n");
                        mat.PrintMatrix();

                        //Penyelesaian
                        resArray = SolusiSPL (mat);
												outputFileEksternal(mat,resArray);
                    } else if (choice  == 2) {
                        // Menampilakan hasil inputan dari file eksternal
                        try {
                            mat.InputMatrixSPL(choice);
                        } catch(FileNotFoundException e) {
                            System.out.print(e);
                        }
                        resArray = new String[mat.kol];

                        // Pencarian solusi menggunakan metode Gauss
                        mat.Matrix2REF();

                        // Menampilkan matriks Row Echelon Form
                        System.out.print("MATRIKS ROW ECHELON FORM \n");
                        mat.PrintMatrix();

                        //Penyelesaian
                        resArray = SolusiSPL (mat);
												outputFileEksternal(mat,resArray);
                    } else { // choice = 3
                        exit = true;
                    }

                } else if (choice  == 2) {
                    MenuInput();
                    choice = pilihanInput();
                    if (choice  == 1) {
                        //Input ukuran matrix dari user
                        System.out.print("Masukkan jumlah baris matrix augmented: ");
                        int nBar = in.nextInt();
                        System.out.print("Masukkan jumlah kolom matrix augmented: ");
                        int nKol = in.nextInt();
                        mat = new Matrix(nBar,nKol);
                        resArray = new String[nKol];

                        // Input permasalahan dari user
                        System.out.println("Masukkan matrix augmented:");
                        mat.InputMatrixSPL(choice);

                        // Pencarian solusi menggunakan metode Gauss-Jordan
                        mat.Matrix2REF();
                        mat.REF2RREF();

                        // Menampilkan matriks Reduced Row Echelon Form
                        System.out.print("MATRIKS REDUCED ROW ECHELON FORM \n");
                        mat.PrintMatrix();

                        //Penyelesaian
                        resArray = SolusiSPL (mat);
												outputFileEksternal(mat,resArray);
                    } else if (choice  == 2) {
                        // Menampilakan hasil inputan dari file eksternal
                        try {
                            mat.InputMatrixSPL(choice);
                        } catch(FileNotFoundException e) {
                            System.out.print(e);
                        }
                        resArray = new String[mat.kol];

                        // Pencarian solusi menggunakan metode Gauss-Jordan
                        mat.Matrix2REF();
                        mat.REF2RREF();

                        // Menampilkan matriks Reduced Row Echelon Form
                        System.out.print("MATRIKS REDUCED ROW ECHELON FORM \n");
                        mat.PrintMatrix();

                        //Penyelesaian
                        resArray = SolusiSPL (mat);
												outputFileEksternal(mat,resArray);

                    } else { // choice = 3
                        exit = true;
                    }
                } else { // choice = 3
                    exit = true;
                }

            } else if (choice  == 2) {
                MenuSolusi();
                choice = inputMenuSolusi();
                if (choice == 1) {
                    MenuInput();
                    choice = pilihanInput();
                    if (choice == 1) {
                        //Input jumlah titik dari user
                        System.out.print("Masukkan jumlah titik: ");
                        int N = in.nextInt();
                        mat = new Matrix(N,N+1);

                        // Input permasalahan dari user
                        System.out.println("Masukkan titik-titik yang akan diinterpolasi: ");
                        mat.InputMatrixInterpolasi(choice);
                        resArray = new String[mat.kol];

                        if (!Solver.IsNotInterpolasiable(mat)) {
                            // Pencarian solusi menggunakan metode Gauss
                            mat.Matrix2REF();

                            // Menampilkan matriks Row Echelon Form
                            System.out.print("MATRIKS ROW ECHELON FORM \n");
                            mat.PrintMatrix();

                            //Penyelesaian
                            resArray = SolusiInterpolasi (mat);
														outputFileEksternal(mat,resArray);
                        } else  {// bila semua titiknya sama
                            System.out.print("Tidak dapat dibuat interpolasinya \n");
                        }
                    } else if (choice == 2) {
                        // Input permasalahan dari file eksternal
                        try {
                            mat.InputMatrixInterpolasi(choice);
                        } catch(FileNotFoundException e) {
                            System.out.print(e);
                        }
                        resArray = new String[mat.kol];

                        if (Solver.IsNotInterpolasiable(mat)) {
                            // Pencarian solusi menggunakan metode Gauss
                            mat.Matrix2REF();

                            // Menampilkan matriks Row Echelon Form
                            System.out.print("MATRIKS ROW ECHELON FORM \n");
                            mat.PrintMatrix();

                            //Penyelesaian
                            resArray = SolusiInterpolasi (mat);
														outputFileEksternal(mat,resArray);
                        } else { // bila semua titiknya sama
                            System.out.print("Tidak dapat dibuat interpolasinya \n");
                        }
                    } else { // choice = 3
                        exit = true;
                    }
                } else if (choice == 2) {
                    MenuInput();
                    choice = pilihanInput();
                    if (choice == 1) {
                        //Input jumlah titik dari user
                        System.out.print("Masukkan jumlah titik: ");
                        int N = in.nextInt();
                        mat = new Matrix(N,N+1);

                        // Input permasalahan dari user
                        System.out.println("Masukkan titik-titik yang akan diinterpolasi: ");
                        mat.InputMatrixInterpolasi(choice);
                        resArray = new String[mat.kol];

                        if (Solver.IsNotInterpolasiable(mat)) {
                            // Pencarian solusi menggunakan metode Gauss - Jordan
                            mat.Matrix2REF();
                            mat.REF2RREF();

                            // Menampilkan matriks Reduced Row Echelon Form
                            System.out.print("MATRIKS REDUCED ROW ECHELON FORM \n");
                            mat.PrintMatrix();

                            //Penyelesaian
                            resArray = SolusiInterpolasi (mat);
														outputFileEksternal(mat,resArray);
                        } else {// bila semua titiknya sama
                            System.out.print("Tidak dapat dibuat interpolasinya \n");
                        }
                    } else if (choice == 2) {
                        // Input permasalahan dari file eksternal
                        try {
                            mat.InputMatrixInterpolasi(choice);
                        } catch(FileNotFoundException e) {
                            System.out.print("File tidak ditemukan!\n");
                        }
                        resArray = new String[mat.kol];
                        mat.Matrix2REF();
                        mat.REF2RREF();

                        if (Solver.IsNotInterpolasiable(mat)) {
                            // Pencarian solusi menggunakan metode Gauss - Jordan
                            mat.Matrix2REF();
                            mat.REF2RREF();

                            // Menampilkan matriks Reduced Row Echelon Form
                            System.out.print("MATRIKS REDUCED ROW ECHELON FORM \n");
                            mat.PrintMatrix();

                            //Penyelesaian
                            resArray = SolusiInterpolasi (mat);
														outputFileEksternal(mat,resArray);
                        } else { // bila semua titiknya sama
                            System.out.print("Tidak dapat dibuat interpolasinya \n");
                        }
                    } else { // choice = 3
                        exit = true;
                    }
                } else { // choice = 3
                    exit = true;
                }
            } else { // choice = 3
                exit = true;
                again = false;
            }

        }
				
				LastMenu ();
        int choice = inputMenuAgain ();

        if (choice == 1) {
            again = true;
        } else  {// choice == 2/3
            again = false;
        }
    }
}
}
