package com.clebson.appmusic.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clebson.appmusic.crud.dto.MusicDTO;
import com.clebson.appmusic.crud.entity.Music;
import com.clebson.appmusic.crud.services.MusicService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;


@RestController
@RequestMapping("/music")
public class MusicController {
	private final MusicService musicService;
	private final PagedResourcesAssembler<MusicDTO> assembler;
	
	@Autowired
	public MusicController(MusicService musicService, PagedResourcesAssembler<MusicDTO> assembler) {
		this.musicService = musicService;
		this.assembler = assembler;
	}

	@GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
	public MusicDTO findById(@PathVariable("id")Long id) {
		MusicDTO music = this.musicService.findById(id);
		music.add(linkTo(methodOn(MusicController.class).findById(id)).withSelfRel());
		return music;
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") int direction) {
		var sortDirection = "desc".equals(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
		
		Page<MusicDTO> musicas = this.musicService.findAll(pageable);
		
		musicas.stream()
			.forEach(m -> m.add(linkTo(methodOn(MusicController.class).findById(m.getId())).withSelfRel()));
		
		PagedModel<EntityModel<MusicDTO>> pagedModel = assembler.toModel(musicas);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public MusicDTO create(@RequestBody MusicDTO music) {
		MusicDTO newMusic = this.musicService.create(music);
		newMusic.add(linkTo(methodOn(MusicController.class).findById(music.getId())).withSelfRel());
		return newMusic;
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public MusicDTO update(@RequestBody MusicDTO music){
		MusicDTO newMusic = this.musicService.update(music);
		newMusic.add(linkTo(methodOn(MusicController.class).findById(music.getId())).withSelfRel());
		return newMusic;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		this.musicService.delete(id);
		return ResponseEntity.ok().build();
	}
}
