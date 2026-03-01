package dev.vladis.TuneWaveApp.dto;

import dev.vladis.TuneWaveApp.document.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumListResponse {
    private Boolean success;
    private List<Album> albums;
}
