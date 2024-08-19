# 🛠️ Backend aplikacji webowej schroniska dla zwierząt
 
## Opis:
Backend aplikacji webowej, która umożliwia wsparcie schroniska dla zwierząt. Napisany w Javie, oparty na frameworku Spring Boot. System zintegrowany jest z PayU API, aby umożliwić obsługę płatności (darowizn) online. 
Aplikacja umożliwia uwierzytelnianie, zarządzanie danymi podopiecznych schroniska. Komunikuje się z bazą danych PostgreSQL.

## 🔧 Wykorzystane technologie:
☕ Java 17, 🚀 Spring Boot, 🔐 Spring Security, 🌐 Spring Web, 💾 Spring Data JPA, 📝 Lombok, 🗄️ PostgreSQL

## Wymagania funkcjonalne
Od strony usera obsługuje: 
- rejestrację, 
- aktywację konta, 
- logowanie, 
- wylogowanie, 
- wypełnianie formularzy adopcyjnych online, 
- pobieranie forumalarza adopcyjnego w formacie PDF, 
- bezpośrednie wysyłanie formularza na skrzynkę pocztową schroniska, 
- przeglądanie zwierząt do adopcji oraz filtrację,
- przekazywanie darowizn pieniężnych na schronisko oraz wybrane zwierzę. 


Od strony administratora umożliwione zostało dodatkowo:
- dodawanie, usuwanie i edytowanie podopiecznych schroniska, 
- aktualizowanie wpisów dotyczących schroniska w poszczególnych zakładkach. 
