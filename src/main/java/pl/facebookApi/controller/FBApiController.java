package pl.facebookApi.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.facebookApi.service.FBConnectionService;

@Api
@RestController
@AllArgsConstructor
public class FBApiController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FBApiController.class);
    private final FBConnectionService fbConnectionService;

    @PostMapping("/adComment")
    public ResponseEntity<?> addNewComment(@RequestParam String postId) {
        LOGGER.info("Adding new comment to post: " + postId);
        return ResponseEntity.ok().body(fbConnectionService.addComment(postId));
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> rdeletePost(@RequestParam String postId) {
        LOGGER.info("Deleting post with id: " + postId);
        return ResponseEntity.ok().body(fbConnectionService.delete(postId));
    }

    @PostMapping("/addPost")
    public ResponseEntity<?> addNewPost(@RequestParam String pageId,  @RequestParam String bm) {
        LOGGER.info("Creating post with id: " + pageId);
        return ResponseEntity.ok().body(fbConnectionService.create(pageId, bm));
    }

    @PostMapping("/seeBM")
    public ResponseEntity<?> seeBM(@RequestParam String businessID, @RequestParam String pageId) {
        LOGGER.info("Creating post with id: " + businessID);
        return ResponseEntity.ok().body(fbConnectionService.pagesFromBM(businessID, pageId));
    }
}
