package pertemuan4pbo;

import java.util.ArrayList;

public class Rekening2 {
    private String nomorRekening2;
    private String namaPemilik;
    protected double saldo;
    private String pin;
    protected ArrayList<Transaksi> riwayatTransaksi;

    public Rekening2(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening2 = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;

        if (!pinValid(pinAwal)) {
            throw new IllegalArgumentException("PIN harus tepat 6 digit angka.");
        }

        this.pin = pinAwal;
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + this.namaPemilik + " berhasil dibuat dengan saldo Rp" + this.saldo);
    }

    public String getNomorRekening() {
        return nomorRekening2;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean autentikasi(String inputPin) {
        return pin.equals(inputPin);
    }

    public boolean otentikasi(String inputPin) {
        return autentikasi(inputPin);
    }

    public boolean gantiPin(String pinLama, String pinBaru) {
        if (!otentikasi(pinLama) || !pinValid(pinBaru)) {
            return false;
        }

        pin = pinBaru;
        return true;
    }

    public static boolean pinValid(String pin) {
        return pin != null && pin.matches("\\d{6}");
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    public boolean tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println("Transaksi Gagal : Minimal nominal penarikan 10.000");
            return false;
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
            return false;
        } else {
            saldo -= nominal;
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
            riwayatTransaksi.add(trxBaru);
            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
            return true;
        }
    }

    public void transfer(Rekening2 tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Transfer gagal: PIN yang Anda masukkan salah!");
            return;
        }
        if (tujuan == null) {
            System.out.println("Transfer gagal: Rekening tujuan tidak ditemukan!");
            return;
        }
        if (tujuan == this) {
            System.out.println("Transfer gagal: Rekening tujuan harus berbeda!");
            return;
        }

        if (tarikTunai(nominal)) {
            tujuan.setorTunai(nominal);
            System.out.println("Transfer ke rekening " + tujuan.getNomorRekening() + " berhasil.");
        }
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening2);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("---------------------");
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini");
            return;
        }

        for (Transaksi transaksi : riwayatTransaksi) {
            transaksi.cetakDetail();
        }
    }
}

