package com.project.familytree.impl;

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
    public Anggota tambahAnggota(Long idAdmin, Anggota anggota) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Id Admin tidak ditemukan"));

        anggota.setAdmin(admin);
        return anggotaRepository.save(anggota);
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
    public Anggota editById(Long id, Long idAdmin, Anggota anggota, MultipartFile image) throws IOException {
        Anggota existingAnggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        existingAnggota.setNama(anggota.getNama());
        existingAnggota.setGender(anggota.getGender());
        existingAnggota.setHubungan(anggota.getHubungan());
        existingAnggota.setTanggalLahir(anggota.getTanggalLahir());

        return anggotaRepository.save(existingAnggota);
    }

    @Override
    public void deleteAnggota(Long id) throws IOException {
        Anggota anggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anggota tidak ditemukan"));

        anggotaRepository.deleteById(id);
    }
}
