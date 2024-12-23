package com.project.familytree.repository;

import com.project.familytree.model.Anggota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
    List<Anggota> findByAdminId(Long idAdmin);

    List<Anggota> findByIdJudulId(Long idJudul);
}
