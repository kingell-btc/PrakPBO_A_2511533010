package pertemuan2pbo;
import java.util.ArrayList;
public class Rekening2 {


	    String nomorRekening2;
	    String namaPemilik;
	    double saldo;
	    ArrayList<Transaksi> riwayatTransaksi;

	    public Rekening2(String nomor, String nama, double saldoAwal) {
	        nomorRekening2 = nomor;
	        namaPemilik = nama;
	        saldo = saldoAwal;
	        riwayatTransaksi = new ArrayList<>();
	        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
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

	    public void tarikTunai(double nominal) {
	        if (nominal < 10000) {
	            System.out.println("Transaksi Gagal : Minimal nomonal penarikan 10.000");
	        } else if (nominal > saldo) {
	            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
	        } else {
	            saldo -= nominal;
	            String idTrx = "TRX-T-" + System.currentTimeMillis();
	            Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
	            riwayatTransaksi.add(trxBaru);
	            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
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
