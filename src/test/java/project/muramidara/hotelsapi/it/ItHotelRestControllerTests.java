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
//        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("Test create hotel functionality")
    public void givenHotelDto_whenCreate_thenSuccessResponse() throws Exception {
        //given
        HotelCreateEditDto createDto = DataUtils.getTestHotelCreateEditDto();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/hotels")
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
        Long existingId = 2L;
        //when
        ResultActions result = mockMvc.perform(put("/api/v1/hotels/"+existingId)
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
        Long incorrectId = -1L;

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/hotels/"+incorrectId)
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
        Long existingId = 1L;

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/hotels/"+existingId));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Grand" +
                        " Plaza Downtown")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description",
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views and a rooftop pool.")));
    }

    @Test
    @DisplayName("Test find by incorrect id functionality")
    public void givenIncorrectId_whenFindById_thenErrorResponse() throws Exception {
        //given
        Long incorrectId = -1L;
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/hotels/"+ incorrectId));
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
        Long existingId = 3L;
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/hotels/" + existingId));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test delete by incorrect id functionality")
    public void givenIncorrectId_whenDelete_thenErrorResponse() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/hotels/-1"));
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
    public void givenHotels_whenFindAll_thenSuccessResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/hotels"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(15)));
    }

    @Test
    @DisplayName("Test find all by filter city functionality")
    public void givenFilter_whenFindAllByFilterCity_thenSuccessResponse() throws Exception {
        //given
        var filterName = "city";
        var filterValue = "London";


        //when
        ResultActions result = mockMvc.perform(get("/api/v1/search")
                .param(filterName,filterValue));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(5)));
    }

    @Test
    @DisplayName("Test find all by incorrect filter functionality")
    public void givenNoFilter_whenFindAllByIncorrectFilter_thenBadRequestResponse() throws Exception {
        //given
                //when
        ResultActions result = mockMvc.perform(get("/api/v1/search"));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test find all group by filter city functionality")
    public void givenFilter_whenFindAllGroupByFilterCity_thenSuccessResponse() throws Exception {
        //given
        var filter = "city";

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/histogram/"+filter));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)));
    }

    @Test
    @DisplayName("Test find all group by incorrect filter functionality")
    public void givenIncorrectFilter_whenFindAllGroupByIncorrectFilter_thenBadRequestResponse() throws Exception {
        //given
        var incorrectFilter= "incorrect";
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/histogram/"+incorrectFilter));
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
        var existingId = 1L;
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/hotels/"+existingId+ "/amenities")
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
                        CoreMatchers.is("Luxury 5-star hotel with panoramic city views and a rooftop pool.")));
    }

    @Test
    @DisplayName("Test add amenities to hotel by incorrect id functionality")
    public void givenIncorrectIdAndAmenities_whenAddAmenities_thenErrorResponse() throws Exception {
        //given
        var amenities = List.of(DataUtils.getTestAmenityDto(), DataUtils.getTestAmenityDto());
        var incorrectId = -1L;
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/hotels/"+incorrectId+ "/amenities")
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
