package com.project.familytree.service;

import com.project.familytree.model.Anggota;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnggotaService {

    List<Anggota> getAllAnggota();

    List<Anggota> getAllByAdmin(Long idAdmin);

    Optional<Anggota> getAnggotaById(Long id);

    Anggota tambahAnggota(Long idAdmin, Anggota anggota);

//    void uploadFotoAnggota(Long idAdmin, Long anggotaId, MultipartFile image) throws IOException;

    Anggota editById(Long id, Long idAdmin, Anggota anggota, MultipartFile image) throws IOException;

    void deleteAnggota(Long id) throws IOException;
}
