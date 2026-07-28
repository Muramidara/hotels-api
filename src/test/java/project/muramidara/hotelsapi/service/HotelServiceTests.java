package project.muramidara.hotelsapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.muramidara.hotelsapi.database.entity.*;
import project.muramidara.hotelsapi.database.repository.*;
import project.muramidara.hotelsapi.dto.AmenityDto;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;
import project.muramidara.hotelsapi.dto.HotelFullReadDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;
import project.muramidara.hotelsapi.exception.HotelNotFoundException;
import project.muramidara.hotelsapi.mapper.AmenityDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelCreateEditDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelFullReadDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelShortReadDtoMapper;
import project.muramidara.hotelsapi.util.DataUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        BDDMockito.given(arrivalTimeRepository.save(any(ArrivalTime.class))).willReturn(DataUtils.getTestArrivalTime());
        BDDMockito.given(contactsRepository.save(any(Contacts.class))).willReturn(DataUtils.getTestContacts());
        BDDMockito.given(addressRepository.save(any(Address.class))).willReturn(DataUtils.getTestAddress());
        BDDMockito.given(hotelCreateEditDtoMapper.map(any(HotelCreateEditDto.class))).willReturn(DataUtils.getTestHotelTransient());
        BDDMockito.given(hotelShortReadDtoMapper.map(any(Hotel.class))).willReturn(DataUtils.getTestHotelShortReadDto());
        //when
        HotelShortReadDto savedHotelDto = hotelService.create(hotelDtoToSave);

        //then
        assertThat(savedHotelDto).isNotNull();
    }

    @Test
    @DisplayName("Test hotel update functionality")
    public void givenHotelToUpdate_whenUpdateHotel_thenRepositoryIsCalled(){
        //given

        HotelCreateEditDto hotelDtoToUpdate = DataUtils.getTestHotelCreateEditDto();
        HotelFullReadDto hotelFullReadDtoThatIsReturned = DataUtils.getTestHotelFullReadDto();
        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.of(DataUtils.getTestHotelPersistent()));
        BDDMockito.given(arrivalTimeRepository.save(any(ArrivalTime.class)))
                .willReturn(DataUtils.getTestArrivalTime());
        BDDMockito.given(contactsRepository.save(any(Contacts.class)))
                .willReturn(DataUtils.getTestContacts());
        BDDMockito.given(addressRepository.save(any(Address.class)))
                .willReturn(DataUtils.getTestAddress());
        BDDMockito.given(hotelCreateEditDtoMapper
                .map( any(HotelCreateEditDto.class), any(Hotel.class)))
                .willReturn(DataUtils.getTestHotelTransient());

        BDDMockito.given(hotelRepository.save(any(Hotel.class))).willReturn(DataUtils.getTestHotelPersistent());
        BDDMockito.given(hotelFullReadDtoMapper.map(any(Hotel.class))).willReturn(hotelFullReadDtoThatIsReturned);


        //when
        HotelFullReadDto updatedHotelDto = hotelService.update(1L, hotelDtoToUpdate);

        //then
        assertThat(updatedHotelDto).isNotNull();
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    @DisplayName("Test update hotel with incorrect id functionality")
    public void givenHotelToUpdateWhereIdIsNotFound_whenUpdateHotel_thenExceptionIsThrown(){
        //given

        HotelCreateEditDto hotelDtoToUpdate = DataUtils.getTestHotelCreateEditDto();
        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(null));

        //when
        assertThrows(HotelNotFoundException.class, () -> hotelService.update(-1L, hotelDtoToUpdate));
        //then
        verify(hotelRepository, never()).save(any(Hotel.class));
    }
    @Test
    @DisplayName("Test find by id hotel functionality")
    public void givenId_whenFindById_thenHotelIsReturned(){
        //given

        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.of(DataUtils.getTestHotelPersistent()));

        BDDMockito.given(hotelFullReadDtoMapper.map(any(Hotel.class))).willReturn(DataUtils.getTestHotelFullReadDto());

        //when
        var obtainedOptionalDto = hotelService.findById(1L);
        //then
        assertTrue(obtainedOptionalDto.isPresent());

    }

    @Test
    @DisplayName("Test find by incorrect id hotel functionality")
    public void givenIncorrectId_whenFindById_thenExceptionIsThrown(){
        //given

        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(null));

        //when
        assertThrows(HotelNotFoundException.class, () -> hotelService.findById(-1L));
        //then
        verify(hotelFullReadDtoMapper, never()).map(any(Hotel.class));
    }


    @Test
    @DisplayName("Test find all by brand filter hotel functionality")
    public void givenFilter_whenFindAllByBrandFilter_thenHotelsAreReturned(){
        //given

        BDDMockito.given(hotelRepository.findAllByBrandIgnoreCase(any(String.class)))
                .willReturn(DataUtils.getTestHotelList());
        BDDMockito.given(hotelShortReadDtoMapper.map(any(Hotel.class)))
                .willReturn(DataUtils.getTestHotelShortReadDto());

        //when
        var obtainedHotelsList = hotelService
                .findAllByFilter("brand", "Marriott");
        //then
        assertFalse(obtainedHotelsList.isEmpty());
        assertThat(obtainedHotelsList).hasSize(1);
    }

    @Test
    @DisplayName("Test add existing amenities to existing hotel id functionality")
    public void givenExistingIdAndExistingAmenities_whenAddAmenities_thenHotelFullReadDtoIsReturned(){
        //given

        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.of(DataUtils.getTestHotelPersistent()));
        BDDMockito.given(amenityDtoMapper.mapFrom(any(AmenityDto.class)))
                .willReturn(DataUtils.getTestAmenityTransient());

        BDDMockito.given(amenityRepository.findAmenityByName(any(String.class)))
                .willReturn(Optional.of(DataUtils.getTestAmenityPersistent()));
        BDDMockito.given(hotelAmenityRepository.save(any(HotelAmenity.class)))
                .willReturn(DataUtils.getTestHotelAmenityPersistent());
        BDDMockito.given(hotelFullReadDtoMapper.map(any(Hotel.class)))
                .willReturn(DataUtils.getTestHotelFullReadDto());

        //when
        var obtainedHotelDto = hotelService
                .addAmenities(1L, List.of(DataUtils.getTestAmenityDto()));
        //then
        assertThat(obtainedHotelDto).isNotNull();
    }

    @Test
    @DisplayName("Test add new amenities to existing hotel id functionality")
    public void givenExistingIdAndNewAmenities_whenAddAmenities_thenHotelFullReadDtoIsReturned(){
        //given

        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.of(DataUtils.getTestHotelPersistent()));
        BDDMockito.given(amenityDtoMapper.mapFrom(any(AmenityDto.class)))
                .willReturn(DataUtils.getTestAmenityTransient());

        BDDMockito.given(amenityRepository.findAmenityByName(any(String.class)))
                .willReturn(Optional.ofNullable(null));
        BDDMockito.given(amenityRepository.save(any(Amenity.class)))
                .willReturn(DataUtils.getTestAmenityPersistent());
        BDDMockito.given(hotelAmenityRepository.save(any(HotelAmenity.class)))
                .willReturn(DataUtils.getTestHotelAmenityPersistent());
        BDDMockito.given(hotelFullReadDtoMapper.map(any(Hotel.class)))
                .willReturn(DataUtils.getTestHotelFullReadDto());

        //when
        var obtainedHotelDto = hotelService
                .addAmenities(1L, List.of(DataUtils.getTestAmenityDto()));
        //then
        assertThat(obtainedHotelDto).isNotNull();
    }

    @Test
    @DisplayName("Test add amenities to incorrect hotel id functionality")
    public void givenIncorrectIdAndAmenities_whenAddAmenities_thenThrowException(){
        //given

        BDDMockito.given(hotelRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(null));

        //when
        assertThrows(HotelNotFoundException.class, () -> hotelService
                .addAmenities(-1L, List.of(DataUtils.getTestAmenityDto())));
        //then
        verify(hotelFullReadDtoMapper, never()).map(any(Hotel.class));
    }

    @Test
    @DisplayName("Test find all hotels functionality")
    public void givenThreeHotels_whenFindAll_thenReturnAllHotels(){
        //given

        var hotel1 = DataUtils.getTestHotelPersistent();
        var hotel2 = DataUtils.getTestHotel2Persistent();
        var hotel3 = DataUtils.getTestHotel3Persistent();
        BDDMockito.given(hotelRepository.findAll())
                .willReturn(List.of(hotel1, hotel2 ,hotel3));
        BDDMockito.given(hotelShortReadDtoMapper.map(any(Hotel.class)))
                .willReturn(DataUtils.getTestHotelShortReadDto());
        //when
        var hotelDtos = hotelService.findAll();
        //then
       assertThat(hotelDtos).isNotNull();
       assertThat(hotelDtos).hasSize(3);
    }

    @Test
    @DisplayName("Test find all hotels grouping by brand functionality")
    public void givenThreeHotelsAndFilter_whenFindAllGroupingByBrandFilter_thenReturnResultMap(){
        //given
        var hotel1 = DataUtils.getTestHotelPersistent();
        var hotel2 = DataUtils.getTestHotel2Persistent();
        var hotel3 = DataUtils.getTestHotel3Persistent();
        BDDMockito.given(hotelRepository.findAll())
                .willReturn(List.of(hotel1, hotel2 ,hotel3));
        //when
        var map = hotelService.findAllGroupByFilter("brand");
        //then
        assertThat(map).isNotNull();
        assertThat(map).hasSize(3);
    }

    @Test
    @DisplayName("Test delete hotel by existing id functionality")
    public void givenExistingId_whenDeleteById_thenTrueIsReturned(){
        //given
        var hotel1 = DataUtils.getTestHotelPersistent();

       BDDMockito.given(hotelRepository.findById(anyLong())).willReturn(Optional.of(hotel1));
        //when
        var result = hotelService.delete(1L);
        //then
        assertTrue(result);

    }

    @Test
    @DisplayName("Test delete hotel by incorrect id functionality")
    public void givenIncorrectId_whenDeleteById_thenExceptionIsThrown(){
        //given
        var hotel1 = DataUtils.getTestHotelPersistent();

        BDDMockito.given(hotelRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //when
        assertThrows(HotelNotFoundException.class, () -> hotelService.delete(1L));
        //then
        verify(hotelRepository, never()).deleteById(anyLong());
    }
}
