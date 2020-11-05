package com.clebson.appmusic.crud.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.clebson.appmusic.crud.entity.Music;
import com.clebson.appmusic.crud.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id","title","path","typeSubscription"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MusicDTO extends RepresentationModel<MusicDTO> implements Serializable{

	private static final long serialVersionUID = -4872514701814420968L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("title")
	private String title;
	
	@JsonProperty("path")
	private String path;
	
	@JsonProperty("typeSubscription")
	private Subscription typeSubscription;
	
	public static MusicDTO createMusicDto(Music music) {
		return new ModelMapper().map(music, MusicDTO.class);
	}
}