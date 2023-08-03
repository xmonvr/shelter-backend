//package pl.shelter.shelterbackend.Adopter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdopterService {
//    @Autowired
//    private AdopterRepository adopterRepository;
//
//    public Adopter createAdopter(Adopter adopter) {
//        return adopterRepository.save(adopter);
//    }
//
//    public Adopter getAdopter(Long id) {
//        return adopterRepository.findById(id);
//    }
//
//    public Adopter updateAdopter(Long id, Adopter adopter) {
//        Adopter existingAdopter = adopterRepository.findById(id);
//        if (existingAdopter == null) {
//            return null;
//        }
//        existingAdopter.setFirstName(adopter.getFirstName());
//        existingAdopter.setLastName(adopter.getLastName());
//        existingAdopter.setPhoneNumber(adopter.getPhoneNumber());
//        existingAdopter.setEmail(adopter.getEmail());
//        existingAdopter.setAddress(adopter.getAddress());
//        existingAdopter.setDateOfBirth(adopter.getDateOfBirth());
//        return adopterRepository.save(existingAdopter);
//    }
//
//    public void deleteAdopter(Long id) {
//        adopterRepository.deleteById(id);
//    }
//}
