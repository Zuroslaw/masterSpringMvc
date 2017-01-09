package masterSpringMvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.ui.Model;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by Zuroslaw on 26.11.2016.
 */

@Controller
public class TwitterController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/")
    public String home() {
      return "searchPage";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue = "hello") String search, Model model) {
        SearchResults searchResults = twitter.searchOperations().search(search);
        List<Tweet> tweets = searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultPage";
    }

    @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      String search = request.getParameter("search");
      if (search.toLowerCase().contains("porn")) {
        redirectAttributes.addFlashAttribute("error", "Nieladnie!");
        return "redirect:/";
      }
      redirectAttributes.addAttribute("search", search);
      return "redirect:result";
    }
}
