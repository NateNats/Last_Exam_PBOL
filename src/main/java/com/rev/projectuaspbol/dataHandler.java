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
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author adyat
 */
public class dataHandler {

    private String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private String userid = "hr";
    private String password = "vito123";

    /*punya reva*/
 /* 
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "system";
    String password = "system";
     */
    Connection conn;

    public void getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userid, password);
            System.out.println("Koneksi berhasil");
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

            String query = "INSERT INTO PESERTA (PESERTAID,NIMPESERTA,NAMAPESERTA,ALAMATPESERTA,PASSWORDPESERTA,TIPEPESERTA) VALUES (?, ?, ?, ?,?,?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, generateRandomId(4));
                preparedStatement.setString(2, dataPeserta.getNim());
                preparedStatement.setString(3, dataPeserta.getNama());
                preparedStatement.setString(4, dataPeserta.getAlamat());
                preparedStatement.setString(5, dataPeserta.getPassword());
                preparedStatement.setString(6, dataPeserta.getTipe());

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "AKUN BERHASIL DITAMBAHKAN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void tambahDataMataKuliahPeserta(String nimPeserta, String pilihan1, String pilihan2, String pilihan3) {
        try {
            getConnection();

            try {
                getConnection();

                // Query untuk memperbarui kolom matkul1, matkul2, dan matkul3 pada tabel Peserta
                String query = "UPDATE PESERTA SET MATAKULIAH1 = ?, MATAKULIAH2 = ?, MATAKULIAH3 = ? WHERE NIMPESERTA = ?";

                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, pilihan1);
                    preparedStatement.setString(2, pilihan2);
                    preparedStatement.setString(3, pilihan3);
                    preparedStatement.setString(4, nimPeserta);

                    // Eksekusi query untuk memperbarui data pada tabel Peserta
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Data mata kuliah peserta berhasil ditambahkan.");
                    } else {
                        System.out.println("Gagal menambahkan data mata kuliah peserta.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    public Peserta cariPeserta(String nim, String password) {
        Peserta pesertaQuery = null;
        String result = null;
        try {
            getConnection();

            String query = "SELECT NIMPESERTA,NAMAPESERTA FROM PESERTA WHERE NIMPESERTA = ? AND PASSWORDPESERTA = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, password);

                try (ResultSet hasil = preparedStatement.executeQuery()) {
                    if (hasil.next()) {
                        String nimPeserta = hasil.getString("NIMPESERTA");
                        String namaPeserta = hasil.getString("NAMAPESERTA");

                        pesertaQuery = new Peserta(nimPeserta, namaPeserta);

                        return pesertaQuery;

                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }

        return pesertaQuery;
    }

    public boolean cekMataKuliahPeserta(String nim) {
        boolean hasMataKuliah = false;

        try {
            getConnection();
            String query = "SELECT MATAKULIAH1, MATAKULIAH2, MATAKULIAH3 FROM PESERTA WHERE NIMPESERTA = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nim);

                try (ResultSet hasil = preparedStatement.executeQuery()) {
                    if (hasil.next()) {
                        String mataKuliah1 = hasil.getString("MATAKULIAH1");
                        String mataKuliah2 = hasil.getString("MATAKULIAH2");
                        String mataKuliah3 = hasil.getString("MATAKULIAH3");

                        // Cek apakah salah satu dari mata kuliah tidak NULL
                        if (mataKuliah1 != null || mataKuliah2 != null || mataKuliah3 != null) {
                            hasMataKuliah = true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }

        return hasMataKuliah;
    }

    public ArrayList<String> ambilMataKuliahPeserta(String nim) {
        ArrayList<String> daftarMataKuliah = new ArrayList<>();

        try {
            getConnection();

            String query = "SELECT MATAKULIAH1, MATAKULIAH2, MATAKULIAH3 FROM PESERTA WHERE NIMPESERTA = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nim);

                try (ResultSet hasil = preparedStatement.executeQuery()) {
                    if (hasil.next()) {
                        String mataKuliah1 = hasil.getString("MATAKULIAH1");
                        String mataKuliah2 = hasil.getString("MATAKULIAH2");
                        String mataKuliah3 = hasil.getString("MATAKULIAH3");

                       
                        if (mataKuliah1 != null) {
                            daftarMataKuliah.add(mataKuliah1);
                        }
                        if (mataKuliah2 != null) {
                            daftarMataKuliah.add(mataKuliah2);
                        }
                        if (mataKuliah3 != null) {
                            daftarMataKuliah.add(mataKuliah3);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }

        return daftarMataKuliah;
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
