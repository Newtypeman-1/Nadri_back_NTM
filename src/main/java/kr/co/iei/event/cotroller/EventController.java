package kr.co.iei.event.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.event.model.dto.EventDTO;
import kr.co.iei.event.model.service.EventService;
import kr.co.iei.util.FileUtils;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/event")
public class EventController {
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private EventService eventService;
	@Value("${file.root}")
	private String root;
	@GetMapping("/{month}")
	public ResponseEntity<List> selectMonthEvent(@PathVariable String month){
		List eventList = eventService.selectMonthEvent(month);
		return ResponseEntity.ok(eventList);
	}
	@GetMapping("/onGoing")
	public ResponseEntity<List> selectOnGoingEvent(@RequestParam String date){
		List eventList = eventService.selectOnGoingEvent(date);
		return ResponseEntity.ok(eventList);
	}
	
	@PostMapping
	public ResponseEntity<Integer> insertEvent(@ModelAttribute EventDTO event,@ModelAttribute MultipartFile img){
		String savepath = root +"/event/thumb/";
		String filepath = fileUtils.upload(savepath, img);
		event.setEventImg(filepath);
		int result = eventService.insertEvent(event);
		return ResponseEntity.ok(result);
	}
	
}
