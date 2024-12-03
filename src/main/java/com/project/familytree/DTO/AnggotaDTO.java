package com.project.familytree.DTO;

public class AnggotaDTO {
    private Long id;
    private String nama;
    private String gender;
    private String hubungan;
    private String tanggalLahir;
    private String fotoAnggota;
    private Long idAdmin;

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

    public String getHubungan() {
        return hubungan;
    }

    public void setHubungan(String hubungan) {
        this.hubungan = hubungan;
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

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }
}
