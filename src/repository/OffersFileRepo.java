package repository;
import domain.SpecialOffer;
import exceptions.RepoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OffersFileRepo {
    private final String fileName;
    Map<String, SpecialOffer> specialOffers;
    /**Constructor file repository pentru oferte
     * @param fileName numele fisierului in care se afla scrise ofertele
     */
    public OffersFileRepo(String fileName) {
        this.fileName = fileName;
        this.specialOffers = new HashMap<>();
        loadData();
    }

    /**
     * Metoda ce citeste ofertele din fisier si le adauga in memorie
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                List<String> attributes = Arrays.asList(line.split(","));
                SpecialOffer entity = extractEntity(attributes);
                try {
                    if (specialOffers.containsKey(entity.getRequirementName()))
                        throw new RepoException("Discount existent!\n");

                    specialOffers.put(entity.getRequirementName(), entity);

                } catch (RepoException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Metoda ce transofrma o list de string-uri intr-o oferta
     * @param attributes lista de atribute in format string pe baza caruia se creeaza oferta
     * @return oferta contruita pe baza arugumentelor
     */
    public SpecialOffer extractEntity(List<String> attributes) {
        return new SpecialOffer(attributes.get(0),Integer.parseInt(attributes.get(1)),attributes.get(2), Double.parseDouble(attributes.get(3)));
    }

    /**Metoda ce returneaza toate ofertele
     * @return ofertele
     */
    public Collection<SpecialOffer> getAll() {
        return specialOffers.values();
    }

}
