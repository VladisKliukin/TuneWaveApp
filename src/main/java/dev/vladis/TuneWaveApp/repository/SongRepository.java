package dev.vladis.TuneWaveApp.repository;

import dev.vladis.TuneWaveApp.document.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {


}
