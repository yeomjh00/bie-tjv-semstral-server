package social_network.web.service;

import static org.assertj.core.api.Assertions.*;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.web.controller.asset.PictureDto;
import social_network.web.domain.Picture;
import social_network.web.repository.media.PictureRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitPictureService {

    @InjectMocks
    PictureService pictureService;

    @Mock
    PictureRepository pictureRepository;

    static List<Picture> pictures = List.of(new Picture[]{
            Picture.builder().id(1L).uri("uri1").height(1).width(1).containedPost(null).build(),
    });

    @BeforeEach
    void init(){
        when(pictureRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        for(var picture : pictures){
            when(pictureRepository.findById(picture.getId())).thenReturn(Optional.of(picture));
        }
    }

    @AfterEach
    void tearDown(){
        reset(pictureRepository);
    }

    @Test
    void readByPostId_valid() {
        //given
        Picture picture = pictures.get(0);

        //when
        ResponseEntity<PictureDto> readById = pictureService.readPictureById(picture.getId());

        //then
        assertThat(readById.getBody()).usingRecursiveComparison().isEqualTo(PictureDto.Picture2Dto(picture));
        assertThat(readById.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void readByPostId_invalid() {
        //given
        Long invalidId = 0L;

        //when
        ResponseEntity<PictureDto> readById = pictureService.readPictureById(invalidId);

        //then
        assertThat(readById.getBody()).isEqualTo(null);
        assertThat(readById.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updatePictureByPictureId_valid() {
        //given
        Picture picture = pictures.get(0);
        PictureDto pictureDto = PictureDto.Picture2Dto(picture);

        //when
        HttpStatus status = pictureService.updatePictureById(picture.getId(), pictureDto);

        //then
        assertThat(status).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updatePictureByPictureId_invalid() {
        //given
        Picture picture = pictures.get(0);
        PictureDto pictureDto = PictureDto.Picture2Dto(picture);
        Long invalidId = 0L;

        //when
        HttpStatus status = pictureService.updatePictureById(invalidId, pictureDto);

        //then
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }
}