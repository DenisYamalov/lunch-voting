package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lunchvoting.restaurant.repository.VoteRepository;
import ru.lunchvoting.restaurant.to.VoteResult;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VoteAdminController.VOTE_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteAdminController {

    static final String VOTE_ADMIN_URL = "/api/admin/votes";
    private VoteRepository repository;

    @GetMapping("/results")
    @Operation(summary = "Get vote results",
            description = "Get vote count for each restaurant on specified date")
    public List<VoteResult> getVoteResults(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<VoteResult> results = repository.getResults(date);
        log.info("get vote results = {}", results);
        return results;
    }
}
