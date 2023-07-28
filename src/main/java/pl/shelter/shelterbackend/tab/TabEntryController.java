package pl.shelter.shelterbackend.tab;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("tab")
@Slf4j
public class TabEntryController {

    private final TabEntryService tabEntryService;

    @PostMapping("/add-volunteering-entry")
    public void addNewVolunteeringEntry(@RequestBody VolunteeringEntry volunteeringEntry) {
        tabEntryService.addNewVolunteeringEntry(volunteeringEntry.getVolunteeringEntry());
    }

    @PostMapping("/add-about-entry")
    public void addNewAboutUsTab(@RequestBody AboutUsEntry aboutUsEntry) {
        log.info("aboutUsEntry --> " + aboutUsEntry.getAboutUsEntry());
        tabEntryService.addNewAboutUsTab(aboutUsEntry.getAboutUsEntry());
    }

    @PostMapping("/add-contact-entry")
    public void addContactEntry(@RequestBody ContactEntry contactEntry) {
        tabEntryService.addContactEntry(contactEntry.getContactEntry());
    }

    @GetMapping("/get-volunteering-entry")
    public VolunteeringEntry getNewVolunteeringEntry() {
        return tabEntryService.getNewVolunteeringEntry();
    }

    @GetMapping("/get-about-entry")
    public AboutUsEntry getNewAboutUsTab() {
       return tabEntryService.getNewAboutUsTab();
    }

    @GetMapping("/get-contact-entry")
    public ContactEntry getContactEntry() {
       return tabEntryService.getContactEntry();
    }
}
