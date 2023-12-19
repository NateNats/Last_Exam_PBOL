/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rev.projectuaspbol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adyat
 */
public class dataHandler {

//    private String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
//    private String userid = "hr";
//    private String password = "vito123";

    /*punya reva*/
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "system";
    String password = "system";

    Connection conn;

    public void getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userid, password);
//            System.out.println("Koneksi berhasil");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Tidak bisa tutup koneksi");
        }
    }

    public void tambahDataPeminjam(Peserta dataPeserta) {
        try {
            getConnection();

            String query = "INSERT INTO PESERTA  VALUES (?, ?, ?, ?,?,?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, generateRandomId(4));
                preparedStatement.setString(2, dataPeserta.getNim());
                preparedStatement.setString(3, dataPeserta.getNama());
                preparedStatement.setString(5, dataPeserta.getAlamat());
                preparedStatement.setString(6, dataPeserta.getPassword());
                preparedStatement.setString(4, dataPeserta.getTipe());

                preparedStatement.executeQuery();
                JOptionPane.showMessageDialog(null, "AKUN BERHASIL DITAMBAHKAN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public int tambahDataMataKuliahPeserta(Peserta peserta, ArrayList<String> matakuliahPilihan) {
        int i = 0;
        for (String matakuliah : matakuliahPilihan) {
            try {
                getConnection();

                String query = "INSERT INTO TRANSAKSI_KULIAH (MATAKULIAH, NIM, NAMAPESERTA, TIPEPESERTA, TOTALBIAYA) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, matakuliah);
                pst.setString(2, peserta.getNim());
                pst.setString(3, peserta.getNama());
                pst.setString(4, "uaaa");
                pst.setInt(5, 100000);
                pst.executeQuery();
                i = 1;

            } catch (SQLException ex) {
                JOptionPane.showConfirmDialog(null, ex);

            } finally {
                close();

            }
        }
        return i;
    }

    public Peserta cariPeserta(String nim, String password) {
        Peserta pesertaQuery = null;

        try {
            getConnection();

            String query = "SELECT * FROM PESERTA WHERE NIMPESERTA = ? AND PASSWORDPESERTA = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, password);

                try (ResultSet hasil = preparedStatement.executeQuery()) {
                    if (hasil.next()) {
                        // Menggunakan nama kolom yang sesuai
                        String nimPeserta = hasil.getString("NIMPESERTA");
                        String namaPeserta = hasil.getString("NAMAPESERTA");
                        String alamat = hasil.getString("ALAMATPESERTA");
                        String tipe = hasil.getString("TIPEPESERTA");

                        // Pastikan urutan parameter konstruktor Peserta sesuai
                        pesertaQuery = new Peserta(namaPeserta, nimPeserta, password, alamat, tipe);

                        return pesertaQuery;
                    }
                }
            }
        } catch (SQLException ex) {
            // Tampilkan pesan kesalahan menggunakan JOptionPane
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            close();
        }

        return pesertaQuery;
    }

    public boolean cekMataKuliahPeserta(String nim) {
        boolean hasMatakuliah = false;

        ArrayList<String> matakuliah = new ArrayList<>();

        String result = null;

        try {
            getConnection();
            String query = "SELECT MATAKULIAH FROM TRANSAKSI_KULIAH WHERE NIMPESERTA = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nim);

            ResultSet hasil = pst.executeQuery();

            while (hasil.next()) {
                String matkul = hasil.getString("MATAKULIAH");
                System.out.println(matkul);
                hasMatakuliah = true;

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        } finally {
            close();
        }

        return hasMatakuliah;
    }

    public ArrayList<String> ambilMataKuliahPeserta(String nim) {
        ArrayList<String> matakuliah = new ArrayList<>();

        try {
            getConnection();

            String query = "SELECT MATAKULIAH FROM TRANSAKSI_KULIAH WHERE NIMPESERTA = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nim);

            ResultSet hasil = pst.executeQuery();

            try {
                while (hasil.next()) {
                    matakuliah.add(hasil.getString("MATAKULIAH"));
                }

                return matakuliah;
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex);

        } finally {
            close();

        }

        return matakuliah;
    }

    public DefaultTableModel tableSetPad(JTable gui) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * TRANSAKSI_KULIAH WHERE MATAKULIAH = Pemrograman Analisis Data");

            while (rs.next()) {
                String nim = rs.getString("NIMPESERTA");
                String nama = rs.getString("NAMAPESERTA");
                String tipe = rs.getString("TIPEPESERTA");

                String[] dataIn = {nim, nama, tipe};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public DefaultTableModel tableSetAlpro(JTable gui) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * TRANSAKSI_KULIAH WHERE MATAKULIAH = Algoritma Program");

            while (rs.next()) {
                String nim = rs.getString("NIMPESERTA");
                String nama = rs.getString("NAMAPESERTA");
                String tipe = rs.getString("TIPEPESERTA");

                String[] dataIn = {nim, nama, tipe};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public DefaultTableModel tableSetIntApp(JTable gui) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * TRANSAKSI_KULIAH WHERE MATAKULIAH = Internet Dan App");

            while (rs.next()) {
                String nim = rs.getString("NIMPESERTA");
                String nama = rs.getString("NAMAPESERTA");
                String tipe = rs.getString("TIPEPESERTA");

                String[] dataIn = {nim, nama, tipe};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public DefaultTableModel tableSetSdnl(JTable gui) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * TRANSAKSI_KULIAH WHERE MATAKULIAH = Struktur Data Non Linear");

            while (rs.next()) {
                String nim = rs.getString("NIMPESERTA");
                String nama = rs.getString("NAMAPESERTA");
                String tipe = rs.getString("TIPEPESERTA");

                String[] dataIn = {nim, nama, tipe};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public DefaultTableModel tableSetPbo(JTable gui) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * TRANSAKSI_KULIAH WHERE MATAKULIAH = Pemrograman Berbasis Objek");

            while (rs.next()) {
                String nim = rs.getString("NIMPESERTA");
                String nama = rs.getString("NAMAPESERTA");
                String tipe = rs.getString("TIPEPESERTA");

                String[] dataIn = {nim, nama, tipe};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public DefaultTableModel tableSetTransaksi(JTable gui, String nim) {
        DefaultTableModel table = (DefaultTableModel) gui.getModel();
        table.setRowCount(0);

        try {
            getConnection();

            String query = "SELECT * TRANSAKSI_KULIAH WHERE NIMPESERTA = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nim);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String idTrans = rs.getString("TRANSAKSIID");
                String matkul = rs.getString("MATAKULIAH");
                String biaya = rs.getString("BIAYA");

                String[] dataIn = {idTrans, matkul, biaya};

                table.addRow(dataIn);
            }

            return table;
        } catch (SQLException ex) {

        } finally {
            close();
        }

        return table;
    }

    public int totalBiaya(String nim) {
        int countBiaya = 0;

        try {
            getConnection();

            String query = "SELECT COUNT(BIAYA) TRANSAKSI_KULIAH WHERE NIMPESERTA = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nim);
            
            try (ResultSet hasil = pst.executeQuery()) {
                if (hasil.next()) {
                    countBiaya = hasil.getInt(1);
                }
            }
        } catch (SQLException ex) {

        }

        return countBiaya;
    }

    public static String generateRandomId(int length) {
        String characters = "0123456789";
        StringBuilder randomId = new StringBuilder("P");

        Random random = new Random();
        for (int i = 1; i < length; i++) {  // Mulai dari indeks 1 karena indeks 0 sudah diisi "P"
            int index = random.nextInt(characters.length());
            randomId.append(characters.charAt(index));
        }

        return randomId.toString();
    }

    public static void main(String[] args) throws SQLException {
        dataHandler dh = new dataHandler();
        dh.getConnection();
        System.out.println(generateRandomId(4));

    }
}
