package pertemuan1pbo;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            ArrayList<Rekening> daftarRekening = new ArrayList<>();
            Rekening akunAktif = null;
            boolean isRunning = true;

            System.out.println("=== SISTEM PERBANKAN MINI ===");

        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = input.nextInt();
            input.nextLine(); 

            switch (pilihan) {
                case 1 -> {
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();
                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();
                    System.out.print("Masukkan Saldo Awal: ");
                    double saldo = input.nextDouble();

                    akunAktif = new Rekening(no, nama, saldo);
                    daftarRekening.add(akunAktif);
                }

                case 2 -> {
                    if (akunAktif == null) {
                        System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        akunAktif.setorTunai(setor); 
                    }
                }

                case 3 -> {
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        System.out.print("Masukkan nominal tarik: ");
                        double tarik = input.nextDouble();
                        akunAktif.tarikTunai(tarik);
                    }
                }

                case 4 -> {
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum membuka rekening!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                }

                case 5 -> {
                    if (daftarRekening.isEmpty()) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        System.out.print("Masukkan No Rekening: ");
                        String nomorDicari = input.nextLine();
                        Rekening rekeningDitemukan = null;

                        for (Rekening rekening : daftarRekening) {
                            if (rekening.nomorRekening.equals(nomorDicari)) {
                                rekeningDitemukan = rekening;
                                break;
                            }
                        }

                        if (rekeningDitemukan == null) {
                            System.out.println("Error: Nomor rekening tidak ditemukan!");
                        } else {
                            akunAktif = rekeningDitemukan;
                            System.out.println("Akun aktif berhasil diganti ke rekening " + akunAktif.nomorRekening + ".");
                        }
                    }
                }

                case 0 -> {
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima kasih!");
                }

                default -> {
                    System.out.println("Pilihan tidak valid!");
                }
            }
            }
        }
    }
}