package com.project.familytree.model;


import javax.persistence.*;

@Entity
@Table(name = "anggota")
public class Anggota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "gender")
    private String gender;

    @Column(name = "tanggal_lahir")
    private String tanggalLahir;

    @Column(name = "foto_anggota")
    private String fotoAnggota;

    @ManyToOne
    @JoinColumn(name = "id_judul", nullable = false)
    private Judul idJudul;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Anggota() {
    }

    public Anggota(Long id, String nama, String gender, String tanggalLahir, String fotoAnggota, Judul idJudul, Admin admin) {
        this.id = id;
        this.nama = nama;
        this.gender = gender;
        this.tanggalLahir = tanggalLahir;
        this.fotoAnggota = fotoAnggota;
        this.idJudul = idJudul;
        this.admin = admin;
    }

    // Getters dan Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Judul getIdJudul() {
        return idJudul;
    }

    public void setIdJudul(Judul idJudul) {
        this.idJudul = idJudul;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getFotoAnggota() {
        return fotoAnggota;
    }

    public void setFotoAnggota(String fotoAnggota) {
        this.fotoAnggota = fotoAnggota;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
