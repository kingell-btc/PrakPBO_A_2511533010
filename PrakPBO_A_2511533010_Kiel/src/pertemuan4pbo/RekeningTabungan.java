package pertemuan4pbo;

public class RekeningTabungan extends Rekening2 {
    private double sukuBunga;

    public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
        super(nomor, nama, saldoAwal, pinAwal);
        this.sukuBunga = sukuBunga;
        System.out.println("Produk: Tabungan Umum | Suku bunga: " + this.sukuBunga + "%");
    }

    public double getSukuBunga() {
        return sukuBunga;
    }

    public void tambahBungaAkhirBulan() {
        double bunga = saldo * (sukuBunga / 100);
        saldo += bunga;

        String idTrx = "TRX-B-" + System.currentTimeMillis();
        Transaksi trxBaru = new Transaksi(idTrx, "Bunga", bunga);
        riwayatTransaksi.add(trxBaru);

        System.out.println("Bunga akhir bulan sebesar Rp" + bunga + " berhasil ditambahkan.");
        System.out.println("Saldo saat ini: Rp" + saldo);
    }

    @Override
    public void cekInformasi() {
        super.cekInformasi();
        System.out.println("Produk         : Tabungan Umum");
        System.out.println("Suku Bunga     : " + sukuBunga + "%");
    }
}
