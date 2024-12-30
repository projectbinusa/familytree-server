package com.project.familytree.impl;

import com.project.familytree.DTO.AnggotaDTO;
import com.project.familytree.exception.NotFoundException;
import com.project.familytree.model.Admin;
import com.project.familytree.model.Anggota;
import com.project.familytree.model.Judul;
import com.project.familytree.repository.AnggotaRepository;
import com.project.familytree.repository.AdminRepository;
import com.project.familytree.repository.JudulRepository;
import com.project.familytree.service.AnggotaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class AnggotaImpl implements AnggotaService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/absensireact.appspot.com/o/%s?alt=media";

    private final AnggotaRepository anggotaRepository;
    private final AdminRepository adminRepository;
    private final JudulRepository judulRepository;

    public AnggotaImpl(AnggotaRepository anggotaRepository, AdminRepository adminRepository, JudulRepository judulRepository) {
        this.anggotaRepository = anggotaRepository;
        this.adminRepository = adminRepository;
        this.judulRepository = judulRepository;
    }

    @Override
    public List<Anggota> getAllAnggota() {
        return anggotaRepository.findAll();
    }

    @Override
    public List<Anggota> getAllByAdmin(Long idAdmin) {
        return anggotaRepository.findByAdminId(idAdmin);
    }

    @Override
    public List<Anggota> getAllByJudul(Long idJudul) {
        return anggotaRepository.findByIdJudulId(idJudul);
    }

    @Override
    public Optional<Anggota> getAnggotaById(Long id) {
        return anggotaRepository.findById(id);
    }

    @Override
    public AnggotaDTO tambahAnggotaDTO(Long idAdmin, AnggotaDTO anggotaDTO) {
        // Cek apakah Admin dengan idAdmin ada
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        // Buat objek Anggota baru
        Anggota anggota = new Anggota();
        anggota.setNama(anggotaDTO.getNama());
        anggota.setGender(anggotaDTO.getGender());
        anggota.setTanggalLahir(anggotaDTO.getTanggalLahir());
        anggota.setAdmin(admin);

        // Cek apakah idJudul ada dan valid
        if (anggotaDTO.getIdJudul() != null) {
            Judul judul = judulRepository.findById(anggotaDTO.getIdJudul())
                    .orElseThrow(() -> new NotFoundException("Id Judul tidak ditemukan"));
            anggota.setIdJudul(judul);
        } else {
            anggota.setIdJudul(null);
        }

        // Simpan Anggota ke database
        Anggota savedAnggota = anggotaRepository.save(anggota);

        // Konversi ke DTO untuk dikembalikan
        AnggotaDTO result = new AnggotaDTO();
        result.setId(savedAnggota.getId());
        result.setNama(savedAnggota.getNama());
        result.setGender(savedAnggota.getGender());
        result.setTanggalLahir(savedAnggota.getTanggalLahir());
        result.setIdAdmin(idAdmin);
        result.setIdJudul(savedAnggota.getIdJudul() != null ? savedAnggota.getIdJudul().getId() : null);

        return result;
    }

//    @Override
//    public void uploadFotoAnggota(Long idAdmin, Long anggotaId, MultipartFile image) throws IOException {
//        Anggota anggota = anggotaRepository.findById(anggotaId)
//                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));
//
//        String imageUrl = uploadImage(image, "anggota_" + idAdmin);
//        anggota.setFotoAnggota(imageUrl);
//        anggotaRepository.save(anggota);
//    }

//    private String uploadImage(MultipartFile file, String fileName) throws IOException {
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String folderPath = "anggota/";
//        String fullPath = folderPath + timestamp + "_" + fileName;
//
//        BlobId blobId = BlobId.of("absensireact.appspot.com", fullPath);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
//
//        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./src/main/resources/FirebaseConfig.json"));
//        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//        storage.create(blobInfo, file.getBytes());
//
//        return String.format(DOWNLOAD_URL, URLEncoder.encode(fullPath, StandardCharsets.UTF_8));
//    }

    @Override
    public AnggotaDTO editAnggotaDTO(Long id, Long idAdmin, AnggotaDTO anggotaDTO) throws IOException {
        // Cek apakah anggota dengan id tersedia
        Anggota existingAnggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        // Cek apakah admin dengan idAdmin tersedia
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        // Update properti anggota
        existingAnggota.setNama(anggotaDTO.getNama());
        existingAnggota.setGender(anggotaDTO.getGender());
        existingAnggota.setTanggalLahir(anggotaDTO.getTanggalLahir());
        existingAnggota.setAdmin(admin);

        // Cek idJudul
        if (anggotaDTO.getIdJudul() != null) {
            Judul judul = judulRepository.findById(anggotaDTO.getIdJudul())
                    .orElseThrow(() -> new NotFoundException("Id Judul tidak ditemukan"));
            existingAnggota.setIdJudul(judul);
        } else {
            existingAnggota.setIdJudul(null);
        }

        // Simpan perubahan
        Anggota updatedAnggota = anggotaRepository.save(existingAnggota);

        // Konversi entity ke DTO
        return mapToDTO(updatedAnggota, idAdmin);
    }

    // Fungsi untuk konversi entity ke DTO
    private AnggotaDTO mapToDTO(Anggota anggota, Long idAdmin) {
        AnggotaDTO result = new AnggotaDTO();
        result.setId(anggota.getId());
        result.setNama(anggota.getNama());
        result.setGender(anggota.getGender());
        result.setTanggalLahir(anggota.getTanggalLahir());
        result.setIdAdmin(idAdmin);
        result.setIdJudul(anggota.getIdJudul() != null ? anggota.getIdJudul().getId() : null);
        return result;
    }



    @Override
    public void deleteAnggota(Long id) throws IOException {
        Anggota anggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        anggotaRepository.deleteById(id);
    }
}
