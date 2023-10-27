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
    public void addNewVolunteeringEntry(@RequestBody String volunteeringEntry) {
        tabEntryService.addNewVolunteeringEntry(volunteeringEntry);
    }

    @PostMapping("/add-about-entry")
    public void addNewAboutUsTab(@RequestBody String aboutUsEntry) {
        tabEntryService.addNewAboutUsTab(aboutUsEntry);
    }

    @PostMapping("/add-contact-entry")
    public void addContactEntry(@RequestBody String contactEntry) {
        tabEntryService.addContactEntry(contactEntry);
    }

    @GetMapping("/get-volunteering-entry")
    public String getNewVolunteeringEntry() {
        return tabEntryService.getNewVolunteeringEntry();
    }

    @GetMapping("/get-about-entry")
    public String getNewAboutUsTab() {
        return tabEntryService.getNewAboutUsTab();
    }

    @GetMapping("/get-contact-entry")
    public String getContactEntry() {
        return tabEntryService.getContactEntry();
    }
}
