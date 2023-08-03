package pl.shelter.shelterbackend.tab;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tab_entries")
@NoArgsConstructor
@Getter
@Setter
public class TabEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tab_entry_id")
    private Long id;
    @Column(name = "volunteering_tab", columnDefinition="TEXT")
    private String volunteeringTab;
    @Column(name = "about_us_tab", columnDefinition="TEXT")
    private String aboutUsTab;
    @Column(name = "contact_tab", columnDefinition="TEXT")
    private String contactTab;

    public TabEntry(String volunteeringTab, String aboutUsTab, String contactTab) {
        this.volunteeringTab = volunteeringTab;
        this.aboutUsTab = aboutUsTab;
        this.contactTab = contactTab;
    }
}
