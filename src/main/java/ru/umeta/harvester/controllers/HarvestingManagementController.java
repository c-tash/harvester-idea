package ru.umeta.harvester.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.umeta.harvester.services.IHarvestingManagementService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

//import ru.umeta.harvesterspring.services.IHarvestingManagementService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HarvestingManagementController {

    private static final Logger logger =
            LoggerFactory.getLogger(HarvestingManagementController.class);

    private final IHarvestingManagementService harvestingManagementService;

    public HarvestingManagementController(
            IHarvestingManagementService harvestingManagementService) {
        this.harvestingManagementService = harvestingManagementService;
    }

    //	/**
    //	 * Simply selects the home view to render by returning its name.
    //	 */
    //	@RequestMapping(value = "/", method = RequestMethod.GET)
    //	public String home(Locale locale, Model model) {
    //		logger.info("Welcome home! The client locale is {}.", locale);
    //
    //		Date date = new Date();
    //		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
    //		String formattedDate = dateFormat.format(date);
    //
    //		model.addAttribute("serverTime", formattedDate );
    //
    //		return "home";
    //	}
    //
    //	@RequestMapping(value = "/", method = RequestMethod.GET)
    //	public String home(Model model) {
    //		return "";
    //	}

    //	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
    //	public String nodes(Locale locale, Model model) {
    //		logger.info("Nodes controller is accessed.");
    //
    //		QueryMessage queryMessage = new QueryMessage();
    //		try {
    //			queryMessage = harvestingManagementService.getQueriesForUser(user, pw);
    //		} catch (Exception e) {
    //
    //		}
    //
    //		if (queryMessage.getText() == null ) {
    //
    //		}
    //		return "nodes";
    //	}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {

        return "register";
    }

    @RequestMapping(value = "/registersubmit", method = RequestMethod.POST)
    public String registerSubmit(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword, Model model) {
        String result = harvestingManagementService.register(username, password);
        return "registersubmit";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "\\upload\\";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                final File newFilePath = new File(filePath + name);
                newFilePath.mkdirs();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFilePath));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
