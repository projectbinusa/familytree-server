package com.project.familytree.repository;

import com.project.familytree.model.Judul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudulRepository extends JpaRepository<Judul, Long> {
    List<Judul> findByAdminId(Long idAdmin);
}
