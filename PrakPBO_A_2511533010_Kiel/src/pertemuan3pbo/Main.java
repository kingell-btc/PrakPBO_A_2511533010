package pertemuan3pbo;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	    public static void main(String[] args) {
	        try (Scanner input = new Scanner(System.in)) {
	            ArrayList<Rekening2> daftarRekening2 = new ArrayList<>();
	            Rekening2 akunAktif = null;
	            boolean isRunning = true;

	            System.out.println("=== SISTEM PERBANKAN MINI ===");

	        while (isRunning) {
	            System.out.println("\nMenu Utama:");
	            System.out.println("1. Buka Rekening Baru");
	            System.out.println("2. Setor Tunai");
	            System.out.println("3. Tarik Tunai");
	            System.out.println("4. Cek Informasi Rekening");
	            System.out.println("5. Ganti Akun");
	            System.out.println("6. Cetak Mutasi (Riwayat)");
	            System.out.println("7. Ganti PIN");
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
	                    input.nextLine();
	                    String pin;
                    do {
	                        System.out.print("Masukkan PIN (tepat 6 digit angka): ");
	                        pin = input.nextLine();
	                        if (!Rekening2.pinValid(pin)) {
	                            System.out.println("PIN harus tepat 6 digit angka!");
	                        }
	                    } while (!Rekening2.pinValid(pin));

	                    akunAktif = new Rekening2(no, nama, saldo, pin);
	                    daftarRekening2.add(akunAktif);
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
	                        System.out.print("Masukkan PIN: ");
	                        String pin = input.nextLine();
	                        if (!akunAktif.otentikasi(pin)) {
	                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
	                            break;
	                        }

	                        System.out.print("Masukkan nominal tarik: ");
	                        double tarik = input.nextDouble();
	                        input.nextLine();
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
	                    if (daftarRekening2.isEmpty()) {
	                        System.out.println("Error: Anda belum memiliki rekening!");
	                    } else {
	                        System.out.print("Masukkan No Rekening: ");
	                        String nomorDicari = input.nextLine();
	                        Rekening2 rekeningDitemukan = null;

	                        for (Rekening2 rekening2 : daftarRekening2) {
	                            if (rekening2.getNomorRekening().equals(nomorDicari)) {
	                                rekeningDitemukan = rekening2;
	                                break;
	                            }
	                        }

	                        if (rekeningDitemukan == null) {
	                            System.out.println("Error: Nomor rekening tidak ditemukan!");
	                        } else {
	                            akunAktif = rekeningDitemukan;
	                            System.out.println("Akun aktif berhasil diganti ke rekening " + akunAktif.getNomorRekening() + ".");
	                        }
	                    }
	                }

	                case 6 -> {
	                    if (akunAktif == null) {
	                        System.out.println("Error: Anda belum membuka rekening!");
	                    } else {
	                        System.out.print("Masukkan PIN: ");
	                        String pin = input.nextLine();
                        if (akunAktif.otentikasi(pin)) {
	                            akunAktif.cetakMutasi();
	                        } else {
	                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
	                        }
	                    }
	                }

	                case 7 -> {
	                    if (akunAktif == null) {
	                        System.out.println("Error: Anda belum membuka rekening!");
	                    } else {
	                        System.out.print("Masukkan PIN lama: ");
	                        String pinLama = input.nextLine();

	                        if (!akunAktif.otentikasi(pinLama)) {
	                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
	                        } else {
	                            String pinBaru;
	                            do {
	                                System.out.print("Masukkan PIN baru (tepat 6 digit angka): ");
	                                pinBaru = input.nextLine();
	                                if (!Rekening2.pinValid(pinBaru)) {
	                                    System.out.println("PIN harus tepat 6 digit angka!");
	                                }
	                            } while (!Rekening2.pinValid(pinBaru));

	                            akunAktif.gantiPin(pinLama, pinBaru);
	                            System.out.println("PIN berhasil diganti.");
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
	    }}