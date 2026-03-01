package dev.vladis.TuneWaveApp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.vladis.TuneWaveApp.document.Album;
import dev.vladis.TuneWaveApp.dto.AlbumListResponse;
import dev.vladis.TuneWaveApp.dto.AlbumRequest;
import dev.vladis.TuneWaveApp.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final Cloudinary cloudinary;

    //--------------------------------------------------------------------------------------------------------------
    public Album addAlbum(AlbumRequest albumRequest) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> imageUploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                albumRequest.getImageFile().getBytes(),
                ObjectUtils.asMap("resource_type", "image")
        );

        Album newAlbum = Album.builder()
                .name(albumRequest.getName())
                .desc(albumRequest.getDesc())
                .bgColor(albumRequest.getBgColor())
                .imageUrl(imageUploadResult.get("secure_url").toString())
                .build();
        return albumRepository.save(newAlbum);
    }

    //--------------------------------------------------------------------------------------------------------------
    public AlbumListResponse getAllAlbums() {
        return new AlbumListResponse(true, albumRepository.findAll());
    }

    //--------------------------------------------------------------------------------------------------------------
    public Boolean removeAlbum(String id) {
        log.info("Removing album with id: {}", id);
        Album existingAlbum = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        albumRepository.delete(existingAlbum);
        return true;
    }
    //--------------------------------------------------------------------------------------------------------------


}
