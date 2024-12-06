package com.project.familytree.controller;

import com.project.familytree.model.Anggota;
import com.project.familytree.service.AnggotaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AnggotaController {

    private final AnggotaService anggotaService;

    public AnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    @GetMapping("/anggota/all")
    public ResponseEntity<List<Anggota>> getAllAnggota() {
        List<Anggota> anggotaList = anggotaService.getAllAnggota();
        return ResponseEntity.ok(anggotaList);
    }

    @GetMapping("/anggota/all-by-admin/{idAdmin}")
    public ResponseEntity<List<Anggota>> getAllAnggotaByAdmin(@PathVariable Long idAdmin) {
        List<Anggota> anggotaList = anggotaService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(anggotaList);
    }

    @GetMapping("/anggota/getById/{id}")
    public ResponseEntity<Anggota> getAnggotaById(@PathVariable Long id) {
        Optional<Anggota> anggota = anggotaService.getAnggotaById(id);
        return anggota.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/anggota/tambahByIdAdmin/{idAdmin}")
    public ResponseEntity<Anggota> tambahAnggota(
            @PathVariable Long idAdmin,
            @RequestBody Anggota anggota) {

        Anggota savedAnggota = anggotaService.tambahAnggota(idAdmin, anggota);
        return ResponseEntity.ok(savedAnggota);
    }

//    @PostMapping(value = "/anggota/uploadFoto/{idAdmin}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Void> uploadFotoAnggota(
//            @PathVariable Long idAdmin,
//            @RequestParam("anggotaId") Long anggotaId,
//            @RequestPart("image") MultipartFile image) throws IOException {
//
//        anggotaService.uploadFotoAnggota(idAdmin, anggotaId, image);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping(value = "/anggota/editById/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Anggota> editAnggota(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestPart(value = "anggota") Anggota anggota,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        Anggota updatedAnggota = anggotaService.editById(id, idAdmin, anggota, image);
        return ResponseEntity.ok(updatedAnggota);
    }

    @DeleteMapping("/anggota/delete/{id}")
    public ResponseEntity<Void> deleteAnggota(@PathVariable Long id) throws IOException {
        anggotaService.deleteAnggota(id);
        return ResponseEntity.noContent().build();
    }
}
