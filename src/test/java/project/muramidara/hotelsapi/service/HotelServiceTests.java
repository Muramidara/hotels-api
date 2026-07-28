package project.muramidara.hotelsapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.repository.*;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;
import project.muramidara.hotelsapi.mapper.AmenityDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelCreateEditDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelFullReadDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelShortReadDtoMapper;
import project.muramidara.hotelsapi.util.DataUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTests {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelAmenityRepository hotelAmenityRepository;
    @Mock
    private AmenityRepository amenityRepository;
    @Mock
    private ContactsRepository contactsRepository;
    @Mock
    private ArrivalTimeRepository arrivalTimeRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private HotelShortReadDtoMapper hotelShortReadDtoMapper;
    @Mock
    private HotelFullReadDtoMapper hotelFullReadDtoMapper;
    @Mock
    private HotelCreateEditDtoMapper hotelCreateEditDtoMapper;
    @Mock
    private AmenityDtoMapper amenityDtoMapper;
    @InjectMocks
    private HotelService hotelService;

    @Test
    @DisplayName("Test save hotel functionality")
    public void givenHotelToSave_whenSaveHotel_thenRepositoryIsCalled(){
        //given
        HotelCreateEditDto hotelDtoToSave = DataUtils.getTestHotelCreateEditDto();
        BDDMockito.given(hotelRepository.save(any(Hotel.class))).willReturn(DataUtils.getTestHotelPersistent());
        BDDMockito.given(hotelCreateEditDtoMapper.map(any(HotelCreateEditDto.class))).willReturn(DataUtils.getTestHotelTransient());
        BDDMockito.given(hotelShortReadDtoMapper.map(any(Hotel.class))).willReturn(DataUtils.getTestHotelShortReadDto());
        //when
        HotelShortReadDto savedHotelDto = hotelService.create(hotelDtoToSave);

        //then
        assertThat(savedHotelDto).isNotNull();
    }
}
