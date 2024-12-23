package com.project.familytree.service;

import com.project.familytree.DTO.AnggotaDTO;
import com.project.familytree.model.Anggota;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnggotaService {

    List<Anggota> getAllAnggota();

    List<Anggota> getAllByAdmin(Long idAdmin);

    List<Anggota> getAllByJudul(Long idJudul);

    Optional<Anggota> getAnggotaById(Long id);

    AnggotaDTO tambahAnggotaDTO(Long idAdmin, AnggotaDTO anggotaDTO);

    AnggotaDTO editAnggotaDTO(Long id, Long idAdmin, AnggotaDTO anggotaDTO) throws IOException;
;
//    void uploadFotoAnggota(Long idAdmin, Long anggotaId, MultipartFile image) throws IOException;

    void deleteAnggota(Long id) throws IOException;
}
