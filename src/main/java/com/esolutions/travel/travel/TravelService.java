package com.esolutions.travel.travel;

import com.esolutions.travel.auth.AppUser;
import com.esolutions.travel.auth.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class TravelService {
    private final TravelRepository repository;
    private final UserRepository users;

    public TravelService(TravelRepository repository, UserRepository users) {
        this.repository = repository;
        this.users = users;
    }

    public Page<TravelResponse> findAll(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);
        return repository.findAllByOwner(currentUser(), PageRequest.of(safePage, safeSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")))
                .map(TravelResponse::from);
    }

    public TravelResponse findById(Long id) {
        return TravelResponse.from(repository.findByIdAndOwner(id, currentUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found")));
    }

    public TravelResponse create(TravelRequest request) {
        return TravelResponse.from(repository.save(new Travel(currentUser(), request.destination(), request.country(),
                request.startDate(), request.endDate(), request.status(), request.notes())));
    }

    public TravelResponse update(Long id, TravelRequest request) {
        Travel travel = repository.findByIdAndOwner(id, currentUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found"));
        travel.update(request.destination(), request.country(), request.startDate(), request.endDate(),
                request.status(), request.notes());
        return TravelResponse.from(repository.save(travel));
    }

    public void delete(Long id) {
        if (!repository.existsByIdAndOwner(id, currentUser())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel not found");
        }
        repository.deleteByIdAndOwner(id, currentUser());
    }

    private AppUser currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUser) {
            return (AppUser) authentication.getPrincipal();
        }
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return users.findByEmail(authentication.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required"));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
    }
}
