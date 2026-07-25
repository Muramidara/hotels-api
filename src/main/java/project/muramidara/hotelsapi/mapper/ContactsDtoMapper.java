package project.muramidara.hotelsapi.mapper;

import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Contacts;
import project.muramidara.hotelsapi.dto.ContactsDto;

@Component
public class ContactsDtoMapper implements BiDirectionalMapper<Contacts, ContactsDto> {
    @Override
    public ContactsDto map(Contacts contacts) {
        return new ContactsDto(
                contacts.getEmail(),
                contacts.getPhone()
        );
    }

    @Override
    public Contacts mapFrom(ContactsDto dto) {
        var contacts = new Contacts();
        contacts.setEmail(dto.getEmail());
        contacts.setPhone(dto.getPhone());
        return contacts;
        }
}
