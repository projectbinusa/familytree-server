package com.project.familytree.service;

import com.project.familytree.DTO.JudulDTO;
import com.project.familytree.model.Judul;

import java.util.List;
import java.util.Optional;

public interface JudulService {

    List<Judul> getAllJudul();

    List<Judul> getAllByAdmin(Long idAdmin);

    Optional<Judul> getJudulById(Long id);

    JudulDTO tambahJudulDTO(Long idAdmin, JudulDTO judulDTO);

    JudulDTO editJudulDTO(Long id, Long idAdmin, JudulDTO judulDTO);

    void deleteJudul(Long id);
}
