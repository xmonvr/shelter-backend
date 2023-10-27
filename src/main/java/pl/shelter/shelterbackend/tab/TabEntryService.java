package pl.shelter.shelterbackend.tab;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TabEntryService {

    private final TabEntryRepository tabEntryRepository;

    public void addNewVolunteeringEntry(String volunteeringEntry) {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElse(new TabEntry());
        tabEntry.setVolunteeringTab(volunteeringEntry);
        tabEntryRepository.save(tabEntry);
    }

    public void addNewAboutUsTab(String aboutUsEntry) {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElse(new TabEntry());
        tabEntry.setAboutUsTab(aboutUsEntry);
        tabEntryRepository.save(tabEntry);
    }

    public void addContactEntry(String contactEntry) {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElse(new TabEntry());
        tabEntry.setContactTab(contactEntry);
        tabEntryRepository.save(tabEntry);
    }

    public String getNewVolunteeringEntry() {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElseThrow();
        return tabEntry.getVolunteeringTab();
    }

    public String getNewAboutUsTab() {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElseThrow();
        return tabEntry.getAboutUsTab();
    }

    public String getContactEntry() {
        TabEntry tabEntry = tabEntryRepository.findById(1L).orElseThrow();
        return tabEntry.getContactTab();
    }
//    public void deleteVolunteeringEntry() {
//        Long tabEntryId = 1L;
//        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
//        tabEntry.setVolunteeringTab(null);
//        tabEntryRepository.save(tabEntry);
//    }
//
//    public void deleteAboutUsEntry() {
//        Long tabEntryId = 1L;
//        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
//        tabEntry.setAboutUsTab(null);
//        tabEntryRepository.save(tabEntry);
//    }
//
//    public void deleteContactEntry() {
//        Long tabEntryId = 1L;
//        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
//        tabEntry.setContactTab(null);
//        tabEntryRepository.save(tabEntry);
//
//    }
}
