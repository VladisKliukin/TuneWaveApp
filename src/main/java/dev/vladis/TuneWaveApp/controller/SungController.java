package dev.vladis.TuneWaveApp.controller;

import dev.vladis.TuneWaveApp.document.Song;
import dev.vladis.TuneWaveApp.dto.SongListResponse;
import dev.vladis.TuneWaveApp.dto.SongRequest;
import dev.vladis.TuneWaveApp.service.SongService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SungController {

    private static final Logger log = LoggerFactory.getLogger(SungController.class);
    private final SongService songService;
    //--------------------------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<?> addSong(@RequestPart("request") String request,
                                     @RequestPart("audio") MultipartFile audioFile,
                                     @RequestPart("image") MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SongRequest songRequest = objectMapper.readValue(request, SongRequest.class);
            songRequest.setAudioFile(audioFile);
            songRequest.setImageFile(imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(songService.addSong(songRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //--------------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        try {
            return ResponseEntity.ok(songService.getAllSongs());
        } catch (Exception e) {
            return ResponseEntity.ok((new SongListResponse(false, null)));
        }
    }



    //--------------------------------------------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            Boolean removed = songService.deleteById(id);

            if (removed) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    //--------------------------------------------------------------------------------------------------------------
}
