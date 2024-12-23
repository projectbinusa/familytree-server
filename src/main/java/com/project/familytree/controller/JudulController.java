package com.project.familytree.controller;

import com.project.familytree.DTO.JudulDTO;
import com.project.familytree.exception.NotFoundException;
import com.project.familytree.model.Judul;
import com.project.familytree.service.JudulService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class JudulController {

    private final JudulService judulService;

    public JudulController(JudulService judulService) {
        this.judulService = judulService;
    }

    @GetMapping("/judul/all")
    public ResponseEntity<List<Judul>> getAllJudul() {
        List<Judul> judulList = judulService.getAllJudul();
        return ResponseEntity.ok(judulList);
    }

    @GetMapping("/judul/all-by-admin/{idAdmin}")
    public ResponseEntity<List<Judul>> getAllJudulByAdmin(@PathVariable Long idAdmin) {
        List<Judul> judulList = judulService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(judulList);
    }

    @GetMapping("/judul/getById/{id}")
    public ResponseEntity<JudulDTO> getJudulById(@PathVariable Long id) {
        Optional<Judul> judul = judulService.getJudulById(id);
        return judul.map(judulEntity -> {
            JudulDTO judulDTO = new JudulDTO();
            judulDTO.setId(judulEntity.getId());
            judulDTO.setJudulKeluarga(judulEntity.getJudulKeluarga());
            judulDTO.setIdAdmin(judulEntity.getAdmin().getId());
            return ResponseEntity.ok(judulDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/judul/tambahByIdAdmin/{idAdmin}")
    public ResponseEntity<JudulDTO> tambahJudul(
            @PathVariable Long idAdmin,
            @RequestBody JudulDTO judulDTO) {
        JudulDTO savedJudul = judulService.tambahJudulDTO(idAdmin, judulDTO);
        return ResponseEntity.ok(savedJudul);
    }

    @PutMapping(value = "/judul/editById/{id}")
    public ResponseEntity<JudulDTO> editJudul(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestBody JudulDTO judulDTO) {
        JudulDTO updatedJudul = judulService.editJudulDTO(id, idAdmin, judulDTO);
        return ResponseEntity.ok(updatedJudul);
    }

    @DeleteMapping("/judul/delete/{id}")
    public ResponseEntity<Void> deleteJudul(@PathVariable Long id) {
        judulService.deleteJudul(id);
        return ResponseEntity.noContent().build();
    }
}
