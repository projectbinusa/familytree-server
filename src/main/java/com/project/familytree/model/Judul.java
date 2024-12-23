package com.project.familytree.model;

import javax.persistence.*;

@Entity
@Table(name = "judul")
public class Judul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "judul_keluarga")
    private String judulKeluarga;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
