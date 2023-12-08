package pl.shelter.shelterbackend.tab;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TabEntryService {

    private final TabEntryRepository tabEntryRepository;

    @Transactional
    public void addNewVolunteeringEntry(String volunteeringEntry) {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
        tabEntry.setVolunteeringTab(volunteeringEntry);
    }

    @Transactional
    public void addNewAboutUsTab(String aboutUsEntry) {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
        tabEntry.setAboutUsTab(aboutUsEntry);
    }

    @Transactional
    public void addContactEntry(String contactEntry) {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElse(new TabEntry());
        tabEntry.setContactTab(contactEntry);
    }

    public String getNewVolunteeringEntry() {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElseThrow();
        return tabEntry.getVolunteeringTab();
    }

    public String getNewAboutUsTab() {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElseThrow();
        return tabEntry.getAboutUsTab();
    }

    public String getContactEntry() {
        Long tabEntryId = 1L;
        TabEntry tabEntry = tabEntryRepository.findById(tabEntryId).orElseThrow();
        return tabEntry.getContactTab();
    }
}
