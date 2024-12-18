package com.project.familytree.impl;

import com.project.familytree.DTO.AnggotaDTO;
import com.project.familytree.exception.NotFoundException;
import com.project.familytree.model.Admin;
import com.project.familytree.model.Anggota;
import com.project.familytree.repository.AnggotaRepository;
import com.project.familytree.repository.AdminRepository;
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

    public AnggotaImpl(AnggotaRepository anggotaRepository, AdminRepository adminRepository) {
        this.anggotaRepository = anggotaRepository;
        this.adminRepository = adminRepository;
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
    public Optional<Anggota> getAnggotaById(Long id) {
        return anggotaRepository.findById(id);
    }

    @Override
    public AnggotaDTO tambahAnggotaDTO(Long idAdmin, AnggotaDTO anggotaDTO) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        Anggota anggota = new Anggota();
        anggota.setNama(anggotaDTO.getNama());
        anggota.setGender(anggotaDTO.getGender());
        anggota.setTanggalLahir(anggotaDTO.getTanggalLahir());
        anggota.setAdmin(admin);

        if (anggotaDTO.getIdAnggota() != null) {
            Anggota parentAnggota = anggotaRepository.findById(anggotaDTO.getIdAnggota())
                    .orElseThrow(() -> new NotFoundException("Id Anggota tidak ditemukan"));
            anggota.setIdAnggota(parentAnggota);
        } else {
            anggota.setIdAnggota(null);
        }

        Anggota savedAnggota = anggotaRepository.save(anggota);

        // Konversi ke DTO
        AnggotaDTO result = new AnggotaDTO();
        result.setId(savedAnggota.getId());
        result.setNama(savedAnggota.getNama());
        result.setGender(savedAnggota.getGender());
        result.setTanggalLahir(savedAnggota.getTanggalLahir());
        result.setIdAdmin(idAdmin);
        if (savedAnggota.getIdAnggota() != null) {
            result.setIdAnggota(savedAnggota.getIdAnggota().getId());  // Mengambil ID anggota parent
        }

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
        Anggota existingAnggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        existingAnggota.setNama(anggotaDTO.getNama());
        existingAnggota.setGender(anggotaDTO.getGender());
        existingAnggota.setTanggalLahir(anggotaDTO.getTanggalLahir());
        existingAnggota.setAdmin(admin);

        if (anggotaDTO.getIdAnggota() != null) {
            Anggota parentAnggota = anggotaRepository.findById(anggotaDTO.getIdAnggota())
                    .orElseThrow(() -> new NotFoundException("Id Anggota tidak ditemukan"));
            existingAnggota.setIdAnggota(parentAnggota);
        } else {
            existingAnggota.setIdAnggota(null);
        }

        // Simpan perubahan
        Anggota updatedAnggota = anggotaRepository.save(existingAnggota);

        // Konversi ke DTO
        AnggotaDTO result = new AnggotaDTO();
        result.setId(updatedAnggota.getId());
        result.setNama(updatedAnggota.getNama());
        result.setGender(updatedAnggota.getGender());
        result.setTanggalLahir(updatedAnggota.getTanggalLahir());
        result.setIdAdmin(idAdmin);
        if (updatedAnggota.getIdAnggota() != null) {
            result.setIdAnggota(updatedAnggota.getIdAnggota().getId());  // Mengambil ID anggota parent
        }

        return result;
    }



    @Override
    public void deleteAnggota(Long id) throws IOException {
        Anggota anggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        anggotaRepository.deleteById(id);
    }
}
