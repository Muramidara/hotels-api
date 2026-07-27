package project.muramidara.hotelsapi.util;

import project.muramidara.hotelsapi.database.entity.Address;
import project.muramidara.hotelsapi.database.entity.Hotel;

public class DataUtils {
    public static Hotel getTestHotelTransient(){
        return Hotel.builder()
                .name("test hotel")
                .brand("Helton")
                .description("description for test hotel")
//                .address(Address.builder()
//                        .city("Minsk")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static Hotel getTestHotel2Transient(){
        return Hotel.builder()
                .name("test hotel 2")
                .brand("Helton")
                .description("description for test hotel 2")
//                .address(Address.builder()
//                        .city("Minsk")
//                        .country("Belarus")
//                        .build()
//                )
                .build();
    }

    public static Hotel getTestHotel3Transient(){
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

    public static Hotel getTestHotelPersistent(){
        return Hotel.builder()
                .id(1L)
                .name("test hotel")
                .brand("test brand")
                .description("description for test hotel")
//                .address(Address.builder()
//                        .id(1L)
//                        .city("Minsk")
//                        .country("Belarus")
//                        .build()
//                )
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
