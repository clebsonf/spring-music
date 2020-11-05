package com.clebson.appmusic.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clebson.appmusic.crud.entity.Music;


@Repository
public interface MusicRepository extends JpaRepository<Music, Long>{

}
