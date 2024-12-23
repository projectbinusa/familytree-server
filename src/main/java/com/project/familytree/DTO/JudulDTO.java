package com.project.familytree.DTO;

public class JudulDTO {
    private Long id;
    private String judulKeluarga;
    private Long idAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudulKeluarga() {
        return judulKeluarga;
    }

    public void setJudulKeluarga(String judulKeluarga) {
        this.judulKeluarga = judulKeluarga;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }
}
