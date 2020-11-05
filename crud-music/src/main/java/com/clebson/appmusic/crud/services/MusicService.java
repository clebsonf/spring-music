package com.clebson.appmusic.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.clebson.appmusic.crud.dto.MusicDTO;
import com.clebson.appmusic.crud.entity.Music;
import com.clebson.appmusic.crud.exception.ResourceNotFoundException;
import com.clebson.appmusic.crud.repository.MusicRepository;

@Service
public class MusicService {

	private final MusicRepository musicRepository;
	
	@Autowired
	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}
	
	public MusicDTO create(MusicDTO music) {
		MusicDTO musicReturn = MusicDTO.createMusicDto(this.musicRepository.save(Music.createMusicEntity(music)));
		return musicReturn;
	}
	
	public Page<MusicDTO> findAll(Pageable pageable){
		var page = this.musicRepository.findAll(pageable);
		return page.map(this::convertToMusicDTO);
	}
	
	public MusicDTO findById(Long id){
		var entity = this.musicRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		return MusicDTO.createMusicDto(entity);
	}
	
	public MusicDTO update(MusicDTO music) {
		this.musicRepository.findById(music.getId())
				.orElseThrow(()-> new ResourceNotFoundException("No records for this ID"));
		return MusicDTO.createMusicDto(this.musicRepository.save(Music.createMusicEntity(music)));
	}

	public void delete(long id) {
		var entity = this.musicRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records for this ID"));
		this.musicRepository.delete(entity);
	}
	
	private MusicDTO convertToMusicDTO(Music music) {
		return MusicDTO.createMusicDto(music);
	}
	
}
