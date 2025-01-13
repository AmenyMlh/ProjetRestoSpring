package tn.uma.isamm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusher.rest.Pusher;

import tn.uma.isamm.entities.Event;

@RestController
@RequestMapping("/event")
public class EventController {
	   @Autowired
	    private Pusher pusher;

	    @PostMapping("/send-event")
	    public String sendEvent(@RequestBody Event event) {
	        try {
	            pusher.trigger("ADMIN", "seuil", event);
	            return "Event sent successfully!";
	        } catch (Exception e) {
	            return "Error: " + e.getMessage();
	        }
	    }
}
