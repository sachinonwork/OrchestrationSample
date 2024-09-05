package co.in.sample.dispatchService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class DispatchController {

    @PostMapping(path = "/dispatch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> billingRequested(@RequestBody DispatchData dispatchData) {
        if (dispatchData != null && dispatchData.getDescription() != null && StringUtils.pathEquals("errorProduct", dispatchData.getDescription())) {
            return ResponseEntity.internalServerError().body("Failed to dispatch");
        }
        return ResponseEntity.ok("Dispatch Completed");
    }
}
