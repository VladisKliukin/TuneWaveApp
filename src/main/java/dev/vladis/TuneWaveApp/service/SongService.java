package dev.vladis.TuneWaveApp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.vladis.TuneWaveApp.document.Album;
import dev.vladis.TuneWaveApp.document.Song;
import dev.vladis.TuneWaveApp.dto.SongListResponse;
import dev.vladis.TuneWaveApp.dto.SongRequest;
import dev.vladis.TuneWaveApp.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final Cloudinary cloudinary;
    //--------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public Song addSong(SongRequest request) throws IOException {

        Map<String, Object> audioUploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                request.getAudioFile().getBytes(), ObjectUtils.asMap("resource_type", "video"));

        Map<String, Object> imageUploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                request.getImageFile().getBytes(), ObjectUtils.asMap("resource_type", "image"));

        Double durationSeconds = (Double) audioUploadResult.get("duration");
        String duration = formatDuration(durationSeconds);

        Song newSong = Song.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .album(request.getAlbum())
                .image(imageUploadResult.get("secure_url").toString())
                .audio(audioUploadResult.get("secure_url").toString())
                .duration(duration)
                .build();

        return songRepository.save(newSong);
    }

    private String formatDuration(Double durationSeconds) {
        if (durationSeconds == null) return "00:00";
        int minutes = (int) (durationSeconds / 60);
        int seconds = (int) (durationSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    //--------------------------------------------------------------------------------------------------------------
    public SongListResponse getAllSongs() {

        return new SongListResponse(true, songRepository.findAll());
    }

    //--------------------------------------------------------------------------------------------------------------


    public Boolean deleteById(String id) {
        Song existingSong = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        songRepository.delete(existingSong);
        return true;
    }

}