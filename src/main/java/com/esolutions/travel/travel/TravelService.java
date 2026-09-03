package com.esolutions.travel.travel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class TravelService {
    private final TravelRepository repository;

    public TravelService(TravelRepository repository) {
        this.repository = repository;
    }

    public List<TravelResponse> findAll() {
        return repository.findAll().stream().map(TravelResponse::from).toList();
    }

    public TravelResponse findById(Long id) {
        return TravelResponse.from(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found")));
    }

    public TravelResponse create(TravelRequest request) {
        return TravelResponse.from(repository.save(new Travel(request.destination(), request.country(),
                request.startDate(), request.endDate(), request.status(), request.notes())));
    }

    public TravelResponse update(Long id, TravelRequest request) {
        Travel travel = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found"));
        travel.update(request.destination(), request.country(), request.startDate(), request.endDate(),
                request.status(), request.notes());
        return TravelResponse.from(repository.save(travel));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found");
        }
        repository.deleteById(id);
    }
}
