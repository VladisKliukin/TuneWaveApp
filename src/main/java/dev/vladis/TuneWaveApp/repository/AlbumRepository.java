package dev.vladis.TuneWaveApp.repository;

import dev.vladis.TuneWaveApp.document.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {

}
