package project.muramidara.hotelsapi.util;

import project.muramidara.hotelsapi.database.entity.Address;
import project.muramidara.hotelsapi.database.entity.ArrivalTime;
import project.muramidara.hotelsapi.database.entity.Contacts;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.dto.*;

public class DataUtils {
    public static Hotel getTestHotelTransient(){
        return Hotel.builder()
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
                .address(getTestAddress())
                .arrivalTime(getTestArrivalTime())
                .contacts(getTestContacts())
                .build();
    }

    public static Hotel getTestHotelMinimalTransient(){
        return Hotel.builder()
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
                .build();
    }

    public static Hotel getTestHotel2MinimalTransient(){
        return Hotel.builder()
                .name("test hotel 2")
                .brand("Helton")
                .description("description for test hotel 2")
                .build();
    }

    public static Hotel getTestHotel3MinimalTransient(){
        return Hotel.builder()
                .name("test hotel 3")
                .brand("Holynight Out")
                .description("description for test hotel 3 ")
//                .address(Address.builder()
//                        .city("Gomel")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static Address getTestAddress(){
        return Address.builder()
                .houseNumber(123)
                .street("Main Street")
                .city("New York")
                .country("USA")
                .postCode("10001")
                .build();
    }

    public static ArrivalTime getTestArrivalTime(){
        var arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn("15:00");
        arrivalTime.setCheckOut("11:00");
        return arrivalTime;
    }

    public static Contacts getTestContacts(){
        var contacts = new Contacts();
        contacts.setPhone("+1-555-0101");
        contacts.setEmail("info@grandplaza.com");
        return contacts;
    }

    public static AddressDto getTestAddressDto() {
        return AddressDto.builder()
                .houseNumber(123)
                .street("Main Street")
                .city("New York")
                .country("USA")
                .postCode("10001")
                .build();
    }

    public static ContactsDto getTestContactsDto() {
        return new ContactsDto("info@grandplaza.com", "+1-555-0101");
    }

    public static ArrivalTimeDto getTestArrivalTimeDto() {
        return new ArrivalTimeDto("15:00", "11:00");
    }

    public static HotelCreateEditDto getTestHotelCreateEditDto(){
        return HotelCreateEditDto.builder()
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
                .address(getTestAddressDto())
                .arrivalTime(getTestArrivalTimeDto())
                .contacts(getTestContactsDto())
                .build();
    }

    public static HotelFullReadDto getTestHotelFullReadDto(){
        return HotelFullReadDto.builder()
                .id(1L)
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
                .address(getTestAddressDto())
                .arrivalTime(getTestArrivalTimeDto())
                .contacts(getTestContactsDto())
                .build();
    }

    public static HotelShortReadDto getTestHotelShortReadDto(){
        return HotelShortReadDto.builder()
                .id(1L)
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .address(String.join(" ",
                                getTestAddressDto().getHouseNumber().toString(),
                                getTestAddressDto().getStreet(),
                                getTestAddressDto().getCity(),
                                getTestAddressDto().getCountry()))
                .phone(getTestContactsDto().getPhone())
                .build();
    }

    public static HotelCreateEditDto getTestHotel2Dto(){
        return HotelCreateEditDto.builder()
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
//                .address(Address.builder()
//                        .city("Minsk")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static HotelCreateEditDto getTestHotel3Dto(){
        return HotelCreateEditDto.builder()
                .name("test hotel 3")
                .brand("Holynight Out")
                .description("description for test hotel 3 ")
//                .address(Address.builder()
//                        .city("Gomel")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static Hotel getTestHotelPersistent(){
        return Hotel.builder()
                .id(1L)
                .name("Grand Plaza Downtown")
                .description("Luxury 5-star hotel with panoramic city views.")
                .brand("Marriott")
                .address(getTestAddress())
                .arrivalTime(getTestArrivalTime())
                .contacts(getTestContacts())
                .build();
    }

    public static Hotel getTestHotel2Persistent(){
        return Hotel.builder()
                .id(2L)
                .name("test hotel 2")
                .brand("test brand 2")
                .description("description for test hotel 2")
//                .address(Address.builder()
//                        .id(2L)
//                        .city("Minsk")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static Hotel getTestHotel3Persistent(){
        return Hotel.builder()
                .id(3L)
                .name("test hotel 3")
                .brand("test brand 3")
                .description("description for test hotel 3 ")
//                .address(Address.builder()
//                        .id(3L)
//                        .city("Gomel")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }
}
