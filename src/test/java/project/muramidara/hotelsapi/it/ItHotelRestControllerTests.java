package project.muramidara.hotelsapi.it;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.muramidara.hotelsapi.database.repository.HotelRepository;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;
import project.muramidara.hotelsapi.dto.HotelFullReadDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;
import project.muramidara.hotelsapi.exception.HotelNotFoundException;
import project.muramidara.hotelsapi.service.HotelService;
import project.muramidara.hotelsapi.util.DataUtils;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItHotelRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setUp(){
        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("Test create hotel functionality")
    public void givenHotelDto_whenCreate_thenSuccessResponse() throws Exception {
        //given
        HotelCreateEditDto createDto = DataUtils.getTestHotelCreateEditDto();
        HotelShortReadDto readDto = DataUtils.getTestHotelShortReadDto();
        //when
        ResultActions result = mockMvc.perform(post("/property-view/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Grand" +
                        " Plaza Downtown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views.")));


    }

    @Test
    @DisplayName("Test update by id hotel functionality")
    public void givenHotelDtoAndExistingId_whenUpdate_thenSuccessResponse() throws Exception {
        //given
        HotelCreateEditDto updateDto = DataUtils.getTestHotelCreateEditDto();
        HotelFullReadDto readDto = DataUtils.getTestHotelFullReadDto();
        BDDMockito.given(hotelService.update(anyLong(), any(HotelCreateEditDto.class))).willReturn(readDto);
        //when
        ResultActions result = mockMvc.perform(put("/property-view/hotels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Grand" +
                        " Plaza Downtown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views.")));


    }

    @Test
    @DisplayName("Test update by incorrect id functionality")
    public void givenHotelDtoAndIncorrectId_whenUpdate_thenErrorResponse() throws Exception {
        //given
        HotelCreateEditDto updateDto = DataUtils.getTestHotelCreateEditDto();
        BDDMockito.given(hotelService.update(anyLong(), any(HotelCreateEditDto.class)))
                .willThrow( new HotelNotFoundException("Hotel not found"));
        //when
        ResultActions result = mockMvc.perform(put("/property-view/hotels/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Hotel not found")));
    }

    @Test
    @DisplayName("Test find by id functionality")
    public void givenExistingId_whenFindById_thenSuccessResponse() throws Exception {
        //given
        HotelFullReadDto readDto = DataUtils.getTestHotelFullReadDto();
        BDDMockito.given(hotelService.findById(anyLong()))
                .willReturn( Optional.of(readDto));
        //when
        ResultActions result = mockMvc.perform(get("/property-view/hotels/1"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Grand" +
                        " Plaza Downtown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views.")));
    }

    @Test
    @DisplayName("Test find by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenErrorResponse() throws Exception {
        //given
        BDDMockito.given(hotelService.findById(anyLong()))
                .willThrow( new HotelNotFoundException("Hotel not found"));
        //when
        ResultActions result = mockMvc.perform(get("/property-view/hotels/-1"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Hotel not found")));
    }

    @Test
    @DisplayName("Test delete by id functionality")
    public void givenExistingId_whenDelete_thenSuccessResponse() throws Exception {
        //given

        BDDMockito.given(hotelService.delete(anyLong()))
                .willReturn(true);
        //when
        ResultActions result = mockMvc.perform(delete("/property-view/hotels/1"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test delete by incorrect id functionality")
    public void givenIncorrectId_whenDelete_thenErrorResponse() throws Exception {
        //given
        BDDMockito.given(hotelService.delete(anyLong()))
                .willThrow( new HotelNotFoundException("Hotel not found"));
        //when
        ResultActions result = mockMvc.perform(delete("/property-view/hotels/-1"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Hotel not found")));
    }

    @Test
    @DisplayName("Test find all hotels functionality")
    public void givenThreeHotels_whenFindAll_thenSuccessResponse() throws Exception {
        //given
        var hotel1 =  DataUtils.getTestHotelShortReadDto();
        var hotel2 =  DataUtils.getTestHotelShortReadDto();
        var hotel3 =  DataUtils.getTestHotelShortReadDto();
        BDDMockito.given(hotelService.findAll())
                .willReturn(List.of(hotel1, hotel2, hotel3));
        //when
        ResultActions result = mockMvc.perform(get("/property-view/hotels"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)));
    }

    @Test
    @DisplayName("Test find all by filter city functionality")
    public void givenHotel_whenFindAllByFilterCity_thenSuccessResponse() throws Exception {
        //given
        var hotel1 =  DataUtils.getTestHotelShortReadDto();

        BDDMockito.given(hotelService.findAllByFilter(anyString(), anyString()))
                .willReturn(List.of(hotel1));
        //when
        ResultActions result = mockMvc.perform(get("/property-view/search")
                .param("city", "Berlin"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("Test find all by incorrect filter functionality")
    public void givenHotel_whenFindAllByIncorrectFilter_thenBadRequestResponse() throws Exception {
        //given
        var hotel1 =  DataUtils.getTestHotelShortReadDto();

        BDDMockito.given(hotelService.findAllByFilter(anyString(), anyString()))
                .willReturn(List.of(hotel1));
        //when
        ResultActions result = mockMvc.perform(get("/property-view/search"));
        //then
        verify(hotelService, never()).findAllByFilter(anyString(), anyString());
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test find all group by filter city functionality")
    public void givenMap_whenFindAllGroupByFilterCity_thenSuccessResponse() throws Exception {
        //given
        Map<String, Long> map = Map.of("Berlin", 3L, "Volgograd", 1L);

        BDDMockito.given(hotelService.findAllGroupByFilter( anyString()))
                .willReturn(map);
        //when
        ResultActions result = mockMvc.perform(get("/property-view/histogram/city"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    @DisplayName("Test find all group by incorrect filter functionality")
    public void givenMap_whenFindAllGroupByIncorrectFilter_thenBadRequestResponse() throws Exception {
        //given
        Map<String, Long> map = Map.of();

        BDDMockito.given(hotelService.findAllGroupByFilter( anyString()))
                .willReturn(map);
        //when
        ResultActions result = mockMvc.perform(get("/property-view/histogram/blabla"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test add amenities to hotel by id functionality")
    public void givenExistingIdAndAmenities_whenAddAmenities_thenSuccessResponse() throws Exception {
        //given
        var amenities = List.of(DataUtils.getTestAmenityDto(), DataUtils.getTestAmenityDto());
        var responseDto = DataUtils.getTestHotelFullReadDto();
        BDDMockito.given(hotelService.addAmenities(anyLong(), any(List.class)))
                .willReturn(responseDto);
        //when
        ResultActions result = mockMvc.perform(post("/property-view/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amenities)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Grand" +
                        " Plaza Downtown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views.")));
    }

    @Test
    @DisplayName("Test add amenities to hotel by incorrect id functionality")
    public void givenIncorrectIdAndAmenities_whenAddAmenities_thenErrorResponse() throws Exception {
        //given
        var amenities = List.of(DataUtils.getTestAmenityDto(), DataUtils.getTestAmenityDto());
        BDDMockito.given(hotelService.addAmenities(anyLong(), any(List.class)))
                .willThrow( new HotelNotFoundException("Hotel not found"));
        //when
        ResultActions result = mockMvc.perform(post("/property-view/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amenities)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Hotel not found")));
    }
}
