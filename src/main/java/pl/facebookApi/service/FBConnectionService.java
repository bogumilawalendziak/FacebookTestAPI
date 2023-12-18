package pl.facebookApi.service;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.Business;
import com.facebook.ads.sdk.Page;
import com.facebook.ads.sdk.PagePost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FBConnectionService {

    private final static String PAGE_ID = "1234567890";
    private static final String TASK = "CREATE_CONTENT";
    private static final String USER_ACCESS_TOKEN = "xxxxxxxxxxxxxxxx";
    private final static Logger LOGGER = LoggerFactory.getLogger(FBConnectionService.class);


    private APIContext getContext() {
        return new APIContext(USER_ACCESS_TOKEN).enableDebug(true);
    }

    private boolean addManagementPermission(String pageId, APIContext context){

    }


    public String create(String id, String bm) {
        try {

            addPermission(id, bm);

            return new Page(id, getContext()).createFeed().setMessage("test test test")
                    .execute().getId();

        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return null;
        }
    }

    private void addPermission(String pageId, String businessId) {
        try {
            var o = new Business(businessId, getContext());
            var u = o.get().execute();
            var q = u.getOwnedPages();
            Optional<String> page = q.execute().stream().map(p -> p.getId()).filter(id -> id.equals(pageId)).findFirst();


            if (page.isPresent()) return page.get();
            var t = o.getOwnedBusinesses().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String delete(String postId) {
        try {
            return new PagePost(postId, getContext()).delete().execute().getRawValue();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return null;
        }
    }

    public String addComment(String postId) {

        try {
            return new PagePost(postId, getContext()).createComment()
                    .setMessage("This is a test comment")
                    .execute().getId();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return null;
        }
    }

    public String likePost(String postId) {
        try {

            return new PagePost(postId, context).createLike().execute().getRawValue();

        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return null;
        }
    }

    public String pagesFromBM(String businessId, String pageId) {

        try {
            var o = new Business(businessId, context);
            var u = o.get().execute();
            var q = u.getOwnedPages();
            var r = q.execute();

            Optional<String> page = q.execute().stream().map(p -> p.getId()).filter(id -> id.equals(pageId)).findFirst();
            if (page.isPresent()) return create(pageId, businessId);
            else return null;


        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return null;
        }
    }
}

