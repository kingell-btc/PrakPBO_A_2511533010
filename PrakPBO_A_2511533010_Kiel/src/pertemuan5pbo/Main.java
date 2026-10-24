package pertemuan4pbo;

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
                System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
                System.out.println("8. Transfer ke Rekening Lain");
                System.out.println("9. Ganti PIN");
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

                        System.out.println("\nPilih Produk: 1. Tabungan Umum | 2. Giro Bisnis");
                        System.out.print("Pilihan: ");
                        int produk = input.nextInt();
                        input.nextLine();

                        Rekening2 rekeningBaru;

                        switch (produk) {
                            case 1 -> {
                                System.out.print("Masukkan sukuBunga (%): ");
                                double sukuBunga = input.nextDouble();
                                input.nextLine();
                                rekeningBaru = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
                            }
                            case 2 -> {
                                System.out.print("Masukkan batasOverdraft: ");
                                double batasOverdraft = input.nextDouble();
                                input.nextLine();
                                rekeningBaru = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
                            }
                            default -> {
                                System.out.println("Pilihan produk tidak valid. Menggunakan Tabungan Umum default.");
                                System.out.print("Masukkan sukuBunga (%): ");
                                double sukuBunga = input.nextDouble();
                                input.nextLine();
                                rekeningBaru = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
                            }
                        }

                        daftarRekening2.add(rekeningBaru);
                        akunAktif = rekeningBaru;
                        System.out.println("Rekening berhasil dibuat dan dipilih sebagai akun aktif.");
                    }

                    case 2 -> {
                        if (akunAktif == null) {
                            System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
                        } else {
                            System.out.print("Masukkan nominal setor: ");
                            double setor = input.nextDouble();
                            input.nextLine();
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
                        } else if (akunAktif instanceof RekeningTabungan) {
                            RekeningTabungan rekeningTabungan = (RekeningTabungan) akunAktif;
                            rekeningTabungan.tambahBungaAkhirBulan();
                        } else {
                            System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
                        }
                    }

                    case 8 -> {
                        if (akunAktif == null) {
                            System.out.println("Error: Anda belum membuka rekening!");
                        } else {
                            System.out.print("Masukkan No Rekening Tujuan: ");
                            String nomorTujuan = input.nextLine();
                            Rekening2 rekeningTujuan = null;

                            for (Rekening2 rekening : daftarRekening2) {
                                if (rekening.getNomorRekening().equals(nomorTujuan)) {
                                    rekeningTujuan = rekening;
                                    break;
                                }
                            }

                            if (rekeningTujuan == null) {
                                System.out.println("Error: Nomor rekening tujuan tidak ditemukan!");
                            } else {
                                System.out.print("Masukkan nominal transfer: ");
                                double nominal = input.nextDouble();
                                input.nextLine();
                                System.out.print("Masukkan PIN: ");
                                String pin = input.nextLine();
                                akunAktif.transfer(rekeningTujuan, nominal, pin);
                            }
                        }
                    }

                    case 9 -> {
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
    }
}
