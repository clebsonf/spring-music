package com.clebson.appmusic.crud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.clebson.appmusic.crud.dto.MusicDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "music")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Music implements Serializable{

	private static final long serialVersionUID = -4872514701814420968L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	@Column(name = "path", nullable = false, length = 255)
	private String path;
	
    @Enumerated(EnumType.STRING)
	private Subscription typeSubscription;
    
    public static Music createMusicEntity(MusicDTO music) {
    	return new ModelMapper().map(music, Music.class);
    }
}
