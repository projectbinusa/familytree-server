package com.project.familytree.impl;

import com.project.familytree.DTO.JudulDTO;
import com.project.familytree.exception.NotFoundException;
import com.project.familytree.model.Admin;
import com.project.familytree.model.Judul;
import com.project.familytree.repository.JudulRepository;
import com.project.familytree.repository.AdminRepository;
import com.project.familytree.service.JudulService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JudulImpl implements JudulService {

    private final JudulRepository judulRepository;
    private final AdminRepository adminRepository;

    public JudulImpl(JudulRepository judulRepository, AdminRepository adminRepository) {
        this.judulRepository = judulRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Judul> getAllJudul() {
        return judulRepository.findAll();
    }

    @Override
    public List<Judul> getAllByAdmin(Long idAdmin) {
        return judulRepository.findByAdminId(idAdmin);
    }

    @Override
    public Optional<Judul> getJudulById(Long id) {
        return judulRepository.findById(id);
    }

    @Override
    public JudulDTO tambahJudulDTO(Long idAdmin, JudulDTO judulDTO) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        Judul judul = new Judul();
        judul.setJudulKeluarga(judulDTO.getJudulKeluarga());
        judul.setAdmin(admin);

        Judul savedJudul = judulRepository.save(judul);

        JudulDTO result = new JudulDTO();
        result.setId(savedJudul.getId());
        result.setJudulKeluarga(savedJudul.getJudulKeluarga());
        result.setIdAdmin(idAdmin);

        return result;
    }

    @Override
    public JudulDTO editJudulDTO(Long id, Long idAdmin, JudulDTO judulDTO) {
        Judul existingJudul = judulRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Judul tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        existingJudul.setJudulKeluarga(judulDTO.getJudulKeluarga());
        existingJudul.setAdmin(admin);

        Judul updatedJudul = judulRepository.save(existingJudul);

        JudulDTO result = new JudulDTO();
        result.setId(updatedJudul.getId());
        result.setJudulKeluarga(updatedJudul.getJudulKeluarga());
        result.setIdAdmin(idAdmin);

        return result;
    }

    @Override
    public void deleteJudul(Long id) {
        judulRepository.deleteById(id);
    }
}
