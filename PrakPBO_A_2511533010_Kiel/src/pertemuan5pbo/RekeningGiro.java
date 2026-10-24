package pertemuan4pbo;
public class RekeningGiro extends Rekening2 {
    private double batasOverdraft;

    public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft)
    {
        // Memanggil inisialisasi dasar dari Superclass
        super(nomor, nama, saldoAwal, pinAwal);
        this.batasOverdraft = batasOverdraft;
    }

    // Getter khusus Giro
    public double getBatasOverdraft() {
        return batasOverdraft;
    }

    @Override
    public boolean tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
            return false;
        }
        if (nominal > saldo + batasOverdraft) {
            System.out.println("Transaksi Gagal: Batas overdraft terlampaui. Saldo Anda: Rp" + saldo
                    + " | Batas overdraft: Rp" + batasOverdraft);
            return false;
        }

        saldo -= nominal;
        String idTrx = "TRX-T-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));
        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        return true;
    }
}