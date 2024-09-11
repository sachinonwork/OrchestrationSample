package co.in.sample.dispatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchController.class);

    @PostMapping(path = "/dispatch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dispatchProcess(@RequestBody DispatchData dispatchData) {
        if (ObjectUtils.isEmpty(dispatchData) || ObjectUtils.isEmpty(dispatchData.getDispatchId())) {
            LOGGER.error("Found request or dispatch Id blank in request");
            return ResponseEntity.internalServerError().body("Failed to dispatch for error product");
        }
        if (dispatchData.getDescription() != null && StringUtils.pathEquals("errorProduct", dispatchData.getDescription())) {
            return ResponseEntity.internalServerError().body("Failed to dispatch");
        }
        LOGGER.info("Order dispatch is done");
        return ResponseEntity.ok("Dispatch Completed");
    }
}
