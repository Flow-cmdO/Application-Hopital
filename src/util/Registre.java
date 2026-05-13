package util;

import model.Entite;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* Gestion des entités principales / CRUD */
public class Registre<T extends Entite> {
    private final Map<Integer, T> entites = new HashMap<>();

    // Create
    public void create(T entite) {
        if (entites.isEmpty()) {
            throw new IllegalArgumentException("L'entite n'existe pas.");
        }
        if (entites.containsKey(entite.getId())) {
            throw new IllegalArgumentException("L'entite avec l'id "+entite.getId()+" existe déjà.");
        }
        entites.put(entite.getId(), entite);
    }

    // Read
    public Optional<T> getById(int id) {
        return Optional.ofNullable(entites.get(id));
    }

    public List<T> getAll(){
        return new ArrayList<>(entites.values());
    }

    public List <T> filtrer(Predicate<T> predicate) {
        return entites.values().stream()
                .filter(predicate).collect(Collectors.toList());
    }

    // Update
    public void update(T entite) {
        if (entites.isEmpty()) {
            throw new IllegalArgumentException("L'entite n'existe pas.");
        }
        if (!entites.containsKey(entite.getId())) {
            throw new IllegalArgumentException("Aucune entité avec l'id " + entite.getId() + " trouvée.");
        }
        entites.put(entite.getId(), entite);
    }

    // Delete
    public void delete(int id) {
        if (entites.isEmpty()) {
            throw new IllegalArgumentException("L'entite n'existe pas.");
        }
        if (!entites.containsKey(id)) {
            throw new IllegalArgumentException("Aucune entité avec l'id " + id + " trouvée.");
        }
        entites.remove(id);
    }

    public boolean contient(int id) {
        return entites.containsKey(id);
    }

    public int taille(){
        return entites.size();
    }

    @Override
    public String toString(){
        return entites.values().stream()
                .map(T::getLabel)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
